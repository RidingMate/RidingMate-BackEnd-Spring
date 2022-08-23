package com.ridingmate.api;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class RidingMateBackEndSpringApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            +"classpath:application.yml,"
            +"classpath:application-aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(RidingMateBackEndSpringApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
//        SpringApplication.run(RidingMateBackEndSpringApplication.class, args);
    }

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }

}
