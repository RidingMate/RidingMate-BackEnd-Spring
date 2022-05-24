package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.FuelRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelService {

    /*
        -내 바이크 연비 리스트 보기
        -내 바이크 주유 등록
        -내 바이크 연비 초기화
     */

    private final AuthService authService;
    private final FuelRepository fuelRepository;
    private final BikeRepository bikeRepository;

    public void list(long bikeId){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeId, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        List<FuelEntity> list = fuelRepository.findByBikeOrderByCreateAt(bikeEntity);

    }
}
