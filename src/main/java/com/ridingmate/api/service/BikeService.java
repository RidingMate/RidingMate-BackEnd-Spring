package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.BikeYearEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.BikeSearchDto;
import com.ridingmate.api.repository.BikeCompanyRepository;
import com.ridingmate.api.repository.BikeModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeCompanyRepository bikeCompanyRepository;

    private final BikeModelRepository bikeModelRepository;

    //바이크 제조사 검색
    public List<BikeSearchDto> searchCompany(){
        return bikeCompanyRepository.findAll()
                .stream()
                .map(BikeCompanyEntity::getBikeCompanyDto)
                .collect(Collectors.toList());
    }

    //TODO : null처리 해야함
    public List<BikeSearchDto> searchModel(String company){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(null);
        return bikeCompanyEntity.getBikeModelSet()
                .stream()
                .sorted(Comparator.comparing(BikeModelEntity::getModel))
                .map(BikeModelEntity::getBikeModelDto)
                .collect(Collectors.toList());
    }

    //TODO : null처리 해야함
    public List<BikeSearchDto> searchYear(String company, String model){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(null);
        BikeModelEntity bikeModelEntity = bikeModelRepository.findByModelAndBikeCompany(model, bikeCompanyEntity).orElseThrow(null);
        return bikeModelEntity.getBikeYearSet()
                .stream()
                .sorted(Comparator.comparing(BikeYearEntity::getYear))
                .map(BikeYearEntity::getBikeYearDto)
                .collect(Collectors.toList());
    }
}
