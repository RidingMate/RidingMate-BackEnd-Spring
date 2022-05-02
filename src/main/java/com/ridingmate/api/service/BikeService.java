package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.BikeInsertRequest;
import com.ridingmate.api.payload.BikeSearchDto;
import com.ridingmate.api.payload.BikeUpdateRequest;
import com.ridingmate.api.repository.BikeCompanyRepository;
import com.ridingmate.api.repository.BikeModelRepository;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.BikeYearRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeService {

    @Autowired
    private final BikeCompanyRepository bikeCompanyRepository;

    @Autowired
    private final BikeModelRepository bikeModelRepository;

    @Autowired
    private final BikeYearRepository bikeYearRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final BikeRepository bikeRepository;

    //바이크 제조사 검색
    public List<BikeSearchDto> searchCompany(){
        return bikeCompanyRepository.findAll()
                .stream()
                .map(BikeCompanyEntity::getBikeCompanyDto)
                .collect(Collectors.toList());
    }

    public List<BikeSearchDto> searchModel(String company){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        return bikeCompanyEntity.getBikeModelSet()
                .stream()
                .sorted(Comparator.comparing(BikeModelEntity::getModel))
                .map(BikeModelEntity::getBikeModelDto)
                .collect(Collectors.toList());
    }

    public List<BikeSearchDto> searchYear(String company, String model){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        BikeModelEntity bikeModelEntity = bikeModelRepository.findByModelAndBikeCompany(model, bikeCompanyEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_MODEL));
        return bikeModelEntity.getBikeYearSet()
                .stream()
                .sorted(Comparator.comparing(BikeYearEntity::getYear))
                .map(BikeYearEntity::getBikeYearDto)
                .collect(Collectors.toList());
    }

    //TODO : Multipart 추가해야함
    public void insertBike(BikeInsertRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(request.getCompany()).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        BikeModelEntity bikeModelEntity = bikeModelRepository.findByModelAndBikeCompany(request.getModel(), bikeCompanyEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_MODEL));
        BikeYearEntity bikeYearEntity = bikeYearRepository.findByYearAndBikeModel(request.getYear(), bikeModelEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_YEAR));

        BikeEntity bikeEntity = BikeEntity.createBike(user, request.getCompany(), request.getModel(), request.getYear(), request.getMileage(), request.getBikeNickName());
        bikeRepository.save(bikeEntity);
    }

    //TODO : Multipart 추가해야함
    public void updateBike(BikeUpdateRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();

    }



    //TODO : 내 바이크 리스트 - 대표바이크 컬럼 없음
    //TODO : 내 바이크 수정
    //TODO : 대표 바이크 등록
    //TODO : 바이크 추가, 정보수정 요청
}
