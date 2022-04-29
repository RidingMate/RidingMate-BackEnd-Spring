package com.ridingmate.api.service;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.payload.BikeCompanyDto;
import com.ridingmate.api.repository.BikeCompanyRepository;
import com.ridingmate.api.repository.BikeModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeCompanyRepository bikeCompanyRepository;

    private final BikeModelRepository bikeModelRepository;

    //바이크 제조사 검색
    public List<BikeCompanyDto> searchCompany(){
        return bikeCompanyRepository.findAll().stream().map(BikeCompanyEntity::getBikeCompanyDto).collect(Collectors.toList());
    }

    //바이크 모델 검색
    public void searchModel(String company){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company);
        Set<BikeModelEntity> bikeModelEntitySet = bikeCompanyEntity.getBikeModelSet();
    }

    //바이크 연식 검색
    public void searchYear(String model){

    }
}
