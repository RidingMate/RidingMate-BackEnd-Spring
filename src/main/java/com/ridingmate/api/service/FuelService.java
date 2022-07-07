package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.FuelDto;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.FuelRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private final AuthService authService;
    private final FuelRepository fuelRepository;
    private final BikeRepository bikeRepository;

    //연비 리스트
    public FuelDto.Response.FuelList list(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        List<FuelEntity> list = fuelRepository.findByBikeOrderByCreateAt(bikeEntity);

        return FuelDto.Response.FuelList.convertEntityToResponse(list, bikeEntity);
    }


    //주유 기록 추가
   @Transactional
    public void addFuel(FuelDto.Request.AddFuel addFuelRequest){
        UserEntity user = authService.getUserEntityByAuthentication();

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
            if(data.getReset() == 'N'){
                totalFuelEfficiency.updateAndGet(v -> new Double((double) (v + data.getFuelEfficiency())));
                totalCountOiling.getAndIncrement();
            }
        });
        bikeEntity.addFuel(fuelEntity.getRecentMileage(), totalFuelEfficiency.get(), totalCountOiling.get());
        bikeRepository.save(bikeEntity);

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    //연비 초기화
    @Transactional
    public void reset(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        fuelRepository.findByBikeAndReset(bikeEntity, 'N').forEach(data->{
            data.reset();
            fuelRepository.save(data);
        });

        bikeEntity.resetFuel();
        bikeRepository.save(bikeEntity);


//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }
}
