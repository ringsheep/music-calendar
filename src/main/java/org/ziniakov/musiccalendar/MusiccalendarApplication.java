package org.ziniakov.musiccalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MusiccalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusiccalendarApplication.class, args);
	}

}
