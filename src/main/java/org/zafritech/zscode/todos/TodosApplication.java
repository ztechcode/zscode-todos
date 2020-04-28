package org.zafritech.zscode.todos;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zafritech.zscode.commons.EnableCommonsModule;

@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
@EnableScheduling
@EnableEurekaClient
@EnableCommonsModule
@SpringBootApplication
public class TodosApplication {

	@PostConstruct
	void started() {
		
	  TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(TodosApplication.class, args);
	}

}
