package com.webmonitor;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = { "com.webmonitor.model" })
@ComponentScan(basePackages = { "com.*" })
@EnableJpaRepositories(basePackages = { "com.webmonitor.repository" })
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class WebMonitor implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(WebMonitor.class, args);

//		String myPsw = new BCryptPasswordEncoder().encode("Adp921alf@");
//		System.out.println("Encrypted: " + myPsw);					
	}

	@Scheduled(cron = "0 */5 * * * MON-FRI")
	void statusRefresher() throws InterruptedException, IOException {		
			
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8084/WebMonitor/applications/getLastUpdates");
		CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				//nothing here yet
			} finally {
				response.close();
				httpClient.close();			
			}		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/applications/**").allowedMethods("*").allowedOrigins("*");

		registry.addMapping("/appSettings/**").allowedMethods("*").allowedOrigins("*");

	}
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {
	
}
