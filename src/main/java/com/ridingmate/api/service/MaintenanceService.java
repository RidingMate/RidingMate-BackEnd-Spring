package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.MaintenanceRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final BikeRepository bikeRepository;
    private final AuthService authService;


    @Transactional
    public List<MaintenanceResponse> getMaintenanceList(Long bike_idx, int year) {
        UserEntity user = authService.getUserEntityByAuthentication();

        // bike idx와 user를 같이 검색
        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx,user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        BikeDto bikeDto = BikeDto.convertEntityToDto(bike);

        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year,12,31);

        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBikeAndDateOfMaintenanceBetween(bike,startDate,endDate);

        return maintenanceEntities.stream()
                .map(maintenanceEntity -> new MaintenanceResponse().convertEntityToResponse(maintenanceEntity, bikeDto))
                .collect(Collectors.toList());
    }

    @Transactional
    public MaintenanceResponse getMaintenanceDetail(Long bike_idx, Long maintenance_idx){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx,user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        BikeDto bikeDto = BikeDto.convertEntityToDto(bike);

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx,bike);

        return new MaintenanceResponse().convertEntityToResponse(maintenanceEntity, bikeDto);
    }

    @Transactional
    public ResponseEntity<ApiResponse> insertMaintenance(MaintenanceInsertRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(),user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = MaintenanceEntity.createMaintenance(request, bike);

        maintenanceRepository.save(maintenanceEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateMaintenance(MaintenanceUpdateRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(),user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(request.getIdx(),bike);
        maintenanceEntity.updateMaintenance(request);

        maintenanceRepository.save(maintenanceEntity);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }


    @Transactional
    public ResponseEntity<ApiResponse> deleteMaintenance(Long bike_idx, Long maintenance_idx){
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx,user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx,bike);

        maintenanceRepository.delete(maintenanceEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }
}
