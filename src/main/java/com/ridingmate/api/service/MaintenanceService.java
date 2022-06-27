package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceCalcByYearResponse;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.MaintenanceRepository;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final BikeRepository bikeRepository;
    private final AuthService authService;
    private final FileService fileService;

    @Transactional
    public MaintenanceCalcByYearResponse getMaintenanceList(Long bike_idx, int year) {
        UserEntity user = authService.getUserEntityByAuthentication();

        // bike idx와 user를 같이 검색
        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        BikeDto bikeDto = BikeDto.convertEntityToDto(bike);

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBikeAndDateOfMaintenanceBetween(bike, startDate, endDate);
        List<MaintenanceResponse> maintenanceResponseList = maintenanceEntities.stream()
                .map(maintenanceEntity -> new MaintenanceResponse().convertEntityToResponse(maintenanceEntity))
                .collect(Collectors.toList());

        int totalAmount = maintenanceResponseList.stream()
                .mapToInt(MaintenanceResponse::getAmount)
                .sum();

        return MaintenanceCalcByYearResponse.builder()
                .maintenanceResponseList(maintenanceResponseList)
                .countMaintenance(maintenanceResponseList.size())
                .totalAmount(totalAmount)
                .bikeDto(bikeDto)
                .build();
    }

    @Transactional
    public MaintenanceResponse getMaintenanceDetail(Long bike_idx, Long maintenance_idx) {
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx, bike);

        return new MaintenanceResponse().convertEntityToResponse(maintenanceEntity);
    }


    @Transactional
    public ResponseEntity<ApiResponse> insertMaintenance(MaintenanceInsertRequest request) {
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(), user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bike.countUpMaintenance();

        MaintenanceEntity maintenanceEntity = MaintenanceEntity.createMaintenance(request, bike);
        MaintenanceEntity savedMaintenance = maintenanceRepository.save(maintenanceEntity);

        if (!request.getFiles().isEmpty()) {
            uploadMultipartFile(request.getFiles(),savedMaintenance,user);
        }

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }


    @Transactional
    public ResponseEntity<ApiResponse> updateMaintenance(MaintenanceUpdateRequest request) {
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(), user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(request.getIdx(), bike);
        maintenanceEntity.updateMaintenance(request);

        MaintenanceEntity updatedMaintenance = maintenanceRepository.save(maintenanceEntity);

        // 프론트에서 삭제된 파일 이름 리스트 넘겨주면 그 이름 삭제
        if (!request.getDeletedFileNameList().isEmpty()){
           try {
               for(String fileName: request.getDeletedFileNameList()){
                   fileService.deleteFile(fileName);
               }
           } catch (Exception e){
               throw new CustomException(ResponseCode.DONT_DELETE_S3_FILE);

           }
        }
        // 새로 들어온 사진만 추가
        if (!request.getFiles().isEmpty()) {
            uploadMultipartFile(request.getFiles(),updatedMaintenance,user);
        }

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }


    @Transactional
    public ResponseEntity<ApiResponse> deleteMaintenance(Long bike_idx, Long maintenance_idx) {
        UserEntity user = authService.getUserEntityByAuthentication();

        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bike.countDownMaintenance();

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx, bike);

        maintenanceRepository.delete(maintenanceEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }


    @Transactional
    void uploadMultipartFile(List<MultipartFile> files, MaintenanceEntity maintenance, UserEntity user){
        try {
            List<FileEntity> fileEntities = fileService.uploadMultipleFile(files, user);
            for (FileEntity file : fileEntities) {
                file.connectMaintenance(maintenance);
            }
        } catch (Exception e) {
            throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
        }
    }

}
