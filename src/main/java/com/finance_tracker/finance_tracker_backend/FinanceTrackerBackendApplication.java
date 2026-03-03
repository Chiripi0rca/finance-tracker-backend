package com.finance_tracker.finance_tracker_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FinanceTrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceTrackerBackendApplication.class, args);
	}


}
