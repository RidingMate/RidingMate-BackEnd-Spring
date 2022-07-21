package com.ridingmate.api.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class BikeServiceTest {

    @Autowired
    private BikeService bikeService;

    @DisplayName("null 입력")
    @Test
    public void nullInput(){
        Assertions.assertNull(bikeService.searchModel(null));
    }

}