package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.FuelDto;
import com.ridingmate.api.repository.bike.BikeRepository;
import com.ridingmate.api.repository.fuel.FuelRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class FuelService {

    /*
        -내 바이크 연비 리스트 보기
        -내 바이크 주유 등록
        -내 바이크 연비 초기화
     */

    private final FuelRepository fuelRepository;
    private final BikeRepository bikeRepository;

    //연비 리스트
    public FuelDto.Response.FuelList list(long bikeIdx, UserEntity user){
        List<FuelEntity> fuelEntities = fuelRepository.fuelList(bikeIdx, user);
        return FuelDto.Response.FuelList.convertEntityToResponse(fuelEntities, fuelEntities.get(0).getBike());
    }


    //주유 기록 추가
   @Transactional
    public void addFuel(FuelDto.Request.AddFuel addFuelRequest, UserEntity user){
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(addFuelRequest.getBike_idx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        if(addFuelRequest.getMileage() < bikeEntity.getMileage()){
            throw new CustomException(ResponseCode.MILEAGE_INPUT_ERROR);
        }
        FuelEntity fuelEntity = new FuelEntity().createEntity(bikeEntity, addFuelRequest);
        fuelRepository.save(fuelEntity);

        AtomicReference<Double> totalFuelEfficiency = new AtomicReference<>((double) 0);
        AtomicInteger totalCountOiling = new AtomicInteger();
        bikeEntity.getFuels().forEach(data->{
            if(data.getResetNum() == 0){
                totalFuelEfficiency.updateAndGet(v -> new Double((double) (v + data.getFuelEfficiency())));
                totalCountOiling.getAndIncrement();
            }
        });
        bikeEntity.addFuel(fuelEntity.getRecentMileage(), totalFuelEfficiency.get(), totalCountOiling.get());
        bikeRepository.save(bikeEntity);
    }



    //TODO : 벌크성 쿼리 Char 에러 생기는듯
    //연비 초기화
    @Transactional
    public void reset(long bikeIdx, UserEntity user){
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        fuelRepository.fuelReset(bikeIdx, user);

        bikeEntity.resetFuel();
        bikeRepository.save(bikeEntity);
    }
}
