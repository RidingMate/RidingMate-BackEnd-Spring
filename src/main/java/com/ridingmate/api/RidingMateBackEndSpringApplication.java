package com.ridingmate.api;

import com.ridingmate.api.dataInsert.DataInsert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RidingMateBackEndSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RidingMateBackEndSpringApplication.class, args);
        DataInsert.jsonParse();
    }

}
