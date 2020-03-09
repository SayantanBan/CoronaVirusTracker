package com.sayantan.coronatracker;

import com.sayantan.coronatracker.service.CoronaVirusDataService;
import com.sayantan.coronatracker.service.impl.CoronaVirusDataServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReactCoronaDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactCoronaDashboardApplication.class, args);
	}

	@Bean
	public CoronaVirusDataService coronaVirusDataService(){
		return new CoronaVirusDataServiceImpl();
	}
}
