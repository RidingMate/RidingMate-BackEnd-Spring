package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.AddBikeEntity;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.AddBikeRequest;
import com.ridingmate.api.payload.user.request.AddFuelRequest;
import com.ridingmate.api.payload.user.response.FuelListResponse;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.FuelRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public FuelListResponse list(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        List<FuelEntity> list = fuelRepository.findByBikeOrderByCreateAt(bikeEntity);

        return new FuelListResponse().convertEntityToResponse(list, bikeEntity);
    }


    //주유 기록 추가
   @Transactional
    public ResponseEntity<ApiResponse> addFuel(AddFuelRequest addFuelRequest){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(addFuelRequest.getBike_idx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        if(addFuelRequest.getMileage() < bikeEntity.getMileage()){
            throw new CustomException(ResponseCode.MILEAGE_INPUT_ERROR);
        }
        FuelEntity fuelEntity = new FuelEntity().createEntity(bikeEntity, addFuelRequest);
        bikeEntity.addFuel(fuelEntity.getRecentMileage(), fuelEntity.getFuelEfficiency());

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }
}