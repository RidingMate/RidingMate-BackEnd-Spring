package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.BikeEntity;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.MaintenanceEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.payload.user.dto.MaintenanceDto.Response.MaintenanceResponse;
import com.ridingmate.api.payload.user.dto.MaintenanceDto.Response.MaintenanceCalcByYearResponse;
import com.ridingmate.api.payload.user.dto.MaintenanceDto.Request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.dto.MaintenanceDto.Request.MaintenanceUpdateRequest;
import com.ridingmate.api.repository.bike.BikeRepository;
import com.ridingmate.api.repository.maintenance.MaintenanceRepository;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.FileService;
import lombok.RequiredArgsConstructor;
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
    public MaintenanceCalcByYearResponse getMaintenanceList(Long bike_idx, int year, UserEntity user) {
//        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
//                new CustomException(ResponseCode.NOT_FOUND_BIKE));
//
//        BikeDto.Request.BikeInfo bikeDto = BikeDto.Request.BikeInfo.convertEntityToDto(bike);
//
//
//        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.findByBikeAndDateOfMaintenanceBetween(bike, startDate, endDate);
//        List<MaintenanceResponse> maintenanceResponseList = maintenanceEntities.stream()
//                .map(MaintenanceResponse::convertEntityToResponse)
//                .collect(Collectors.toList());

//        int totalAmount = maintenanceResponseList.stream()
//                .mapToInt(MaintenanceResponse::getAmount)
//                .sum();
//
//        return MaintenanceCalcByYearResponse.builder()
//                .maintenanceResponseList(maintenanceResponseList)
//                .countMaintenance(maintenanceResponseList.size())
//                .totalAmount(totalAmount)
//                .bikeDto(bikeDto)
//                .build();

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<MaintenanceEntity> maintenanceEntities = maintenanceRepository.list(bike_idx,user,startDate, endDate);

        int totalAmount = maintenanceEntities.stream().mapToInt(MaintenanceEntity::getAmount).sum();

        BikeDto.Request.BikeInfo bikeInfo = null;
        if(maintenanceEntities.size() > 0){
            bikeInfo = BikeDto.Request.BikeInfo.convertEntityToDto(maintenanceEntities.get(0).getBike());
        }


        return MaintenanceCalcByYearResponse.builder()
                .maintenanceResponseList(maintenanceEntities.stream().map(MaintenanceResponse::convertEntityToResponse).collect(Collectors.toList()))
                .countMaintenance(maintenanceEntities.size())
                .totalAmount(totalAmount)
                .bikeDto(bikeInfo)
                .build();
    }

    @Transactional
    public MaintenanceResponse getMaintenanceDetail(Long bike_idx, Long maintenance_idx, UserEntity user) {
//        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
//                new CustomException(ResponseCode.NOT_FOUND_BIKE));
//
//        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx, bike);

        MaintenanceEntity maintenanceEntity = maintenanceRepository.maintenanceDetail(bike_idx, maintenance_idx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        return MaintenanceResponse.convertEntityToResponse(maintenanceEntity);
    }


    @Transactional
    public void insertMaintenance(MaintenanceInsertRequest request, UserEntity user) {
        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(), user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bike.countUpMaintenance();

        MaintenanceEntity maintenanceEntity = MaintenanceEntity.createMaintenance(request, bike);
        MaintenanceEntity savedMaintenance = maintenanceRepository.save(maintenanceEntity);

        if (!request.getFiles().isEmpty()) {
            uploadMultipartFile(request.getFiles(), savedMaintenance, user);
        }
    }


    @Transactional
    public void updateMaintenance(MaintenanceUpdateRequest request, UserEntity user) {
        BikeEntity bike = bikeRepository.findByIdxAndUser(request.getBike_idx(), user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(request.getIdx(), bike);
        maintenanceEntity.updateMaintenance(request);

        MaintenanceEntity updatedMaintenance = maintenanceRepository.save(maintenanceEntity);

        // 프론트에서 삭제된 파일 이름 리스트 넘겨주면 그 이름 삭제
        if (!request.getDeletedFileNameList().isEmpty()) {
            try {
                for (String fileName : request.getDeletedFileNameList()) {
                    fileService.deleteFile(fileName);
                }
            } catch (Exception e) {
                throw new CustomException(ResponseCode.DONT_DELETE_S3_FILE);

            }
        }
        // 새로 들어온 사진만 추가
        if (!request.getFiles().isEmpty()) {
            uploadMultipartFile(request.getFiles(), updatedMaintenance, user);
        }
    }


    @Transactional
    public void deleteMaintenance(Long bike_idx, Long maintenance_idx, UserEntity user) {

        BikeEntity bike = bikeRepository.findByIdxAndUser(bike_idx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bike.countDownMaintenance();

        MaintenanceEntity maintenanceEntity = maintenanceRepository.findByIdxAndBike(maintenance_idx, bike);
        maintenanceRepository.delete(maintenanceEntity);
    }


    @Transactional
    void uploadMultipartFile(List<MultipartFile> files, MaintenanceEntity maintenance, UserEntity user) {
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
