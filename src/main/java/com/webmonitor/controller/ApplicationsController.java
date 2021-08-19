package com.webmonitor.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webmonitor.component.LastDataRefresh;
import com.webmonitor.model.Applications;
import com.webmonitor.model.JobStatus;
import com.webmonitor.repository.ApplicationsRepository;
import com.webmonitor.repository.ExternalDataSource;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "*")
public class ApplicationsController {

	@Autowired
	private ApplicationsRepository applicationRepository;
	
	@Autowired
	LastDataRefresh lastDataRefresh;
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Applications>> listApps() {

		List<Applications> list = applicationRepository.findAll();
		Collections.sort(list);

		return new ResponseEntity<List<Applications>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Optional<Applications>> findApplication(@PathVariable Long id) {

		Optional<Applications> list = applicationRepository.findById(id);

		return new ResponseEntity<Optional<Applications>>(list, HttpStatus.OK);

	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Applications> addApplications(@RequestBody Applications application) {

		Applications savedApplication = applicationRepository.save(application);

		return new ResponseEntity<Applications>(savedApplication, HttpStatus.OK);
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Applications> updateApplications(@RequestBody Applications application) {

		Applications savedApplication = applicationRepository.save(application);

		return new ResponseEntity<Applications>(savedApplication, HttpStatus.OK);
	}

	@GetMapping(value = "/getLastUpdates", produces = "application/json")
	public void checkDataAndJobUpdates() {

		List<Applications> listOfApps = applicationRepository.findAll();

		for (int i = 0; i < (listOfApps.toArray()).length; i++) {

			Applications appToUpdate = (Applications) listOfApps.toArray()[i];
			
			JobStatus jbStatus = getLDU(appToUpdate);
					
			if (jbStatus.getLastDataUpdate() != null) {

				appToUpdate.setLastDataUpdate(jbStatus.getLastDataUpdate());
				appToUpdate.setJobStatus(jbStatus.getJobStatus());
				appToUpdate.setLastJobStart(jbStatus.getLastJobRun());
				appToUpdate.setLastCubeUpdate(jbStatus.getLastCubeUpdate());
				appToUpdate.setCurrentPeriod(jbStatus.getLastMonthlyClosure());

				applicationRepository.save(appToUpdate);
				lastDataRefresh.setLastDataRefresh(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa").format(new Date()));
				
			}
			
			jbStatus = null;
			appToUpdate = null;			

		}
	}

	@GetMapping(value = "/getLastDataRefresh", produces = "text/plain")
	public ResponseEntity<String> getLastDataRefresh() throws ParseException {
								
		return new ResponseEntity<String>(lastDataRefresh.getLastDataRefresh(), HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "del/{id}", produces = "application/json")
	public String delApplicationsById(@PathVariable Long id) {

		Optional<Applications> appToDelete = applicationRepository.findById(id);

		if (appToDelete != null) {
			applicationRepository.deleteById(id);
		} else {
			return "application not found";
		}

		return "Application deleted";
	}

	private JobStatus getLDU(Applications myApp) {
		
		JobStatus jobStatus = new JobStatus();
		
		if (myApp.getDbServer() != "" && myApp.getDbServer() != null) {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(ExternalDataSource.getDataSource(myApp.getDbServer(),
					myApp.getDbName(), myApp.getDbUser(), myApp.getDbPsw()));

			String sqlStatement = "EXEC msdb..sp_help_job NULL, '"+myApp.getExtractionJobName().trim()+"', 'JOB'";
			
			jdbcTemplate.query(sqlStatement, rs -> {
				if (rs.next()) {
					
					jobStatus.setCurrentExecutionStatus(rs.getString("current_execution_status"));
					jobStatus.setLastRunOutcome(rs.getString("last_run_outcome"));
					
					return "ok";
				}
				return "";
			});								
			
			sqlStatement = "select " + 
					"	top 1" + 
					"	Cast(msdb.dbo.Agent_DateTime(jh.run_date,jh.run_time) as smalldatetime) lastJobRun,	" + 
					"	Case " + 
					"		When ja.start_execution_date Is Not Null And ja.stop_execution_date Is Null Then 3 " + 
					"		else jh.run_status " + 
					"	end as jobStatus, "+
					"	(select Cast(Date_Journ as smalldatetime) from BWS_Dates) as lastDataUpdate," + 
					"	(select Cast(Date_Cube  as smalldatetime) from BWS_Dates) as lastCubeUpdate, " +
					"  (select cast(cast(Periode as int) as char(8)) from BWS_Dates) as lastMontlhyClosure " +
					"from  " + 
					"	msdb..sysjobs jb " + 
					"inner join " + 
					"	msdb..sysjobhistory jh " + 
					"	on jh.job_id = jb.job_id " + 
					"   and jh.step_id = jb.start_step_id " +
					"left join " + 
					"	msdb.dbo.sysjobactivity ja " + 
					"	ON ja.job_id = jb.job_id	 " + 
					"	and Convert(date, start_execution_date, 120) = (SELECT  " + 
					"														Max(Convert(date, start_execution_date, 120)) " + 
					"													FROM  " + 
					"														msdb.dbo.sysjobactivity AS sja  " + 
					"													INNER JOIN  " + 
					"														msdb.dbo.sysjobs AS sj  " + 
					"														ON sja.job_id = sj.job_id  " + 
					"													WHERE  " + 
					"														sja.start_execution_date IS NOT NULL    " + 
					"														AND sja.stop_execution_date IS NULL " + 
					"														And sj.name = '"+myApp.getExtractionJobName().trim()+"') " +
					"where " + 
					"	name = '"+myApp.getExtractionJobName().trim()+"' " + 
					"order by  " + 
					"	msdb.dbo.Agent_DateTime(jh.run_date,jh.run_time) desc";
			
			
			jdbcTemplate.query(sqlStatement, rs -> {
				if (rs.next()) {
					
//					last_run_outcome	int	Outcome of the job the last time it ran:
//
//						0 = Failed
//						1 = Succeeded
//						3 = Canceled
//						5 = Unknown
//
//						current_execution_status	int	Current execution status:
//
//						1 = Executing
//						2 = Waiting For Thread
//						3 = Between Retries
//						4 = Idle
//						5 = Suspended
//						6 = Obsolete
//						7 = PerformingCompletionActions

					String currentStatus = jobStatus.getCurrentExecutionStatus();
					String lastOutcome = jobStatus.getLastRunOutcome();
					
					try {
						jobStatus.setLastJobRun(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("lastJobRun")));
					} catch (ParseException e) { 
						e.printStackTrace();
					}
					
					if (currentStatus.equalsIgnoreCase("4")) {
						if (lastOutcome.equalsIgnoreCase("3")) {
							jobStatus.setJobStatus("0");
						} else {
						jobStatus.setJobStatus(jobStatus.getLastRunOutcome());
						}
					} else if (currentStatus.equalsIgnoreCase("1")) {
						jobStatus.setJobStatus("3");
					}
					
					jobStatus.setLastMonthlyClosure(rs.getString("lastMontlhyClosure"));

					try {
						jobStatus.setLastDataUpdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("lastDataUpdate")));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					try {
						jobStatus.setLastCubeUpdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("lastCubeUpdate")));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					System.out.println(jobStatus);
					return "ok";
				}
				return "";
			});			
						
		}

		return jobStatus;
	}
}
