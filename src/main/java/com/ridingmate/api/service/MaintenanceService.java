package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
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

    @Transactional
    public List<MaintenanceResponse> getMaintenanceList(Long bike_id) {
        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBikeIdx(bike_id);
        return maintenanceEntities.stream().map(maintenanceEntity ->
                new MaintenanceResponse().convertEntityToResponse(maintenanceEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public MaintenanceResponse getMaintenanceDetail(Long bike_id, Long maintenance_id){
        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByBikeIdxAndIdx(bike_id,maintenance_id);
        return new MaintenanceResponse().convertEntityToResponse(maintenanceEntity);
    }

    @Transactional
    public ResponseEntity<ApiResponse> insertMaintenance(MaintenanceInsertRequest request){
        MaintenanceEntity maintenanceEntity = MaintenanceEntity.createMaintenance(request);
        maintenanceRepository.save(maintenanceEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateMaintenance(MaintenanceUpdateRequest request){
        // 바이크정보
        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(request.getIdx(),request.getBike());
        maintenanceEntity.updateMaintenance(request);

        maintenanceRepository.save(maintenanceEntity);
        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));


    }

}
