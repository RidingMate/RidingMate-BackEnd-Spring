package com.ridingmate.api.service;

import com.ridingmate.api.exception.CustomException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class BikeServiceTest {

    @Autowired
    BikeService bikeService;

    @Test
    public void bikeModelSearch(){
        Assertions.assertThrows(Exception.class, ()->
                bikeService.searchModel("zzz")
        );
//        bikeService.searchModel("G 310 R");
    }

}