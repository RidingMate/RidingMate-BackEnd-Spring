package com.ridingmate.api.service;

import com.ridingmate.api.repository.*;
import com.ridingmate.api.repository.bike.BikeRepository;
import com.ridingmate.api.repository.bikeCompany.BikeCompanyRepository;
import com.ridingmate.api.repository.bikeModel.BikeModelRepository;
import com.ridingmate.api.repository.bikeYear.BikeYearRepository;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class BikeServiceTest {

    @Autowired
    BikeService bikeService;
    @Autowired
    BikeCompanyRepository bikeCompanyRepository;
    @Autowired
    BikeModelRepository bikeModelRepository;
    @Autowired
    BikeYearRepository bikeYearRepository;
    @Autowired
    AuthService authService;
    @Autowired
    BikeRepository bikeRepository;
    @Autowired
    AddBikeRepository addBikeRepository;
    @Autowired
    FileService fileService;
    @Autowired
    FileRepository fileRepository;


    @Test
    public void searchCompanyTest(){
        bikeService.searchCompany();
    }


}