package com.snackman.datnud11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@ComponentScan({"com.snackman.datnud11.controller","com.snackman.datnud11.controller.auth",
		"com.snackman.datnud11.services.imp",
		"com.snackman.datnud11.utils",
		"com.snackman.datnud11.config"})
@EnableJpaRepositories
		("com.snackman.datnud11.repo")
@EntityScan("com.snackman.datnud11.entity")
@EnableCaching
public class DatnUd11Application {

	public static void main(String[] args) {
		SpringApplication.run(DatnUd11Application.class, args);
	}
}
