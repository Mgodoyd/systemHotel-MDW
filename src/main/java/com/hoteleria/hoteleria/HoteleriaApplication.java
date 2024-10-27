package com.hoteleria.hoteleria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hoteleria.hoteleria.interfaces")
public class HoteleriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoteleriaApplication.class, args);
	}

}
