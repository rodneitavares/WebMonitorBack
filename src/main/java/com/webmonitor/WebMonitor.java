package com.webmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"com.webmonitor.model"})
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.webmonitor.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class WebMonitor implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(WebMonitor.class, args);
		
//		String myPsw = new BCryptPasswordEncoder().encode("1234");
//		System.out.println("Encrypted: " + myPsw);					
	}
		
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/applications/**")
			.allowedMethods("*")
			.allowedOrigins("*");
		
		registry.addMapping("/appSettings/**")
		.allowedMethods("*")
		.allowedOrigins("*");
		
		}

}
