package com.snackman.datnud11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@ComponentScan({"com.snackman.datnud11.controller",
		"com.snackman.datnud11.services.imp",
		"com.snackman.datnud11.utils"})
@EnableJpaRepositories
		("com.snackman.datnud11.repo")
@EntityScan("com.snackman.datnud11.entity")
public class DatnUd11Application {

	public static void main(String[] args) {
		SpringApplication.run(DatnUd11Application.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
