package com.brewery.client.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.yaml")
public class BreweryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreweryClientApplication.class, args);
    }

}
