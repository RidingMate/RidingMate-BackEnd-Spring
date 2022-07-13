package com.ridingmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RidingMateBackEndSpringApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            +"classpath:application.yml,"
            +"classpath:application-aws.yml,"
            +"classpath:application-oauth2.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(RidingMateBackEndSpringApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
//        SpringApplication.run(RidingMateBackEndSpringApplication.class, args);
    }

}
