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

			String sqlStatement = "select " + 
					"	top 1" + 
					"	Cast(msdb.dbo.Agent_DateTime(jh.run_date,jh.run_time) as smalldatetime) lastJobRun,	" + 
					"	jh.run_status as jobStatus," + 
					"	(select Cast(Date_Journ as smalldatetime) from BWS_Dates) as lastDataUpdate," + 
					"	(select Cast(Date_Cube  as smalldatetime) from BWS_Dates) as lastCubeUpdate, " +
					"  (select cast(cast(Periode as int) as char(8)) from BWS_Dates) as lastMontlhyClosure " +
					"from  " + 
					"	msdb..sysjobs jb " + 
					"inner join " + 
					"	msdb..sysjobhistory jh " + 
					"	on jh.job_id = jb.job_id " + 
					"   and jh.step_id = jb.start_step_id " +
					"where " + 
					"	name = '"+myApp.getExtractionJobName().trim()+"' " + 
					"order by  " + 
					"	msdb.dbo.Agent_DateTime(jh.run_date,jh.run_time) desc";

			@SuppressWarnings("unused")
			String result = jdbcTemplate.query(sqlStatement, rs -> {
				if (rs.next()) {
					
					try {
						jobStatus.setLastJobRun(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("lastJobRun")));
					} catch (ParseException e) { 
						e.printStackTrace();
					}
					
					jobStatus.setJobStatus(rs.getString("jobStatus"));
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
					
					return "ok";
				}
				return "";
			});			
						
		}

		return jobStatus;
	}
}
