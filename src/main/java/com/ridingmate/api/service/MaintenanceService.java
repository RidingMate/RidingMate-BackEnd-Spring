package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final BikeRepository bikeRepository;


    //3 bike entity에서 maintenance 정보 불러오기,,, 근데 말이 안되는것같긴 함
//    public List<MaintenanceResponse> getMaintenanceList(Long bike_id) {
//        BikeEntity bike = bikeRepository.findById(bike_id).orElseThrow(()->
//                new CustomException(ResponseCode.NOT_FOUND_BIKE));
//
//        List<MaintenanceEntity> maintenances = bike.getMaintenances(); // 이게 말이 되나,,,?
//
//        return maintenances.stream().map(maintenanceEntity ->
//                new MaintenanceResponse().convertEntityToResponse(maintenanceEntity))
//                .collect(Collectors.toList());
//    }

    //     2 bike id로 bike entity 가져와서 그걸로 찾기
    public List<MaintenanceResponse> getMaintenanceList(Long bike_id) {

        BikeEntity bike = bikeRepository.findById(bike_id).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBike(bike);

        return maintenanceEntities.stream().map(maintenanceEntity ->
                        new MaintenanceResponse().convertEntityToResponse(maintenanceEntity))
                .collect(Collectors.toList());
    }

    // 1 bikeIdx로 찾기...?
//    @Transactional
//    public List<MaintenanceResponse> getMaintenanceList(Long bike_id) {
//        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBikeIdx(bike_id);
//        return maintenanceEntities.stream().map(maintenanceEntity ->
//                new MaintenanceResponse().convertEntityToResponse(maintenanceEntity))
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public MaintenanceResponse getMaintenanceDetail(Long bike_id, Long maintenance_id){
//        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByBikeIdxAndIdx(bike_id,maintenance_id);
//        return new MaintenanceResponse().convertEntityToResponse(maintenanceEntity);
//    }
//
//    @Transactional
//    public ResponseEntity<ApiResponse> insertMaintenance(Long bike_id, MaintenanceInsertRequest request){
//        MaintenanceEntity maintenanceEntity = MaintenanceEntity.createMaintenance(request);
//        maintenanceRepository.save(maintenanceEntity);
//
//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
//    }
//
//    @Transactional
//    public ResponseEntity<ApiResponse> updateMaintenance(Long bike_id, Long maintenance_id,MaintenanceUpdateRequest request){
//        // 바이크정보
//        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(request.getIdx(),request.getBike());
//        maintenanceEntity.updateMaintenance(request);
//
//        maintenanceRepository.save(maintenanceEntity);
//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
//    }

}
