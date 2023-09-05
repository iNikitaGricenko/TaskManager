package com.wolfhack.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:database.properties"),
    @PropertySource("classpath:domain.properties"),
    @PropertySource("classpath:web.properties")
})
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}