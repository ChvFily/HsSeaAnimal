package com.hs.sea_water;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling // 开启定时任务功能
@SpringBootApplication
public class SeaWaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeaWaterApplication.class, args);
	}

}
