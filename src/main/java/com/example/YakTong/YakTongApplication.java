package com.example.YakTong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YakTongApplication {

	public static void main(String[] args) {
		SpringApplication.run(YakTongApplication.class, args);
	}

}
