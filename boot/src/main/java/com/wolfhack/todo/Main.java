package com.wolfhack.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:database.properties"),
    @PropertySource("classpath:domain.properties"),
    @PropertySource("classpath:web.properties")
})
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}