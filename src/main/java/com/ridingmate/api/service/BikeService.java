package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.BikeInsertRequest;
import com.ridingmate.api.payload.user.dto.BikeSearchDto;
import com.ridingmate.api.payload.user.request.BikeUpdateRequest;
import com.ridingmate.api.repository.BikeCompanyRepository;
import com.ridingmate.api.repository.BikeModelRepository;
import com.ridingmate.api.repository.BikeRepository;
import com.ridingmate.api.repository.BikeYearRepository;
import com.ridingmate.api.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeCompanyRepository bikeCompanyRepository;
    private final BikeModelRepository bikeModelRepository;
    private final BikeYearRepository bikeYearRepository;
    private final AuthService authService;
    private final BikeRepository bikeRepository;

    //바이크 제조사 검색
    public List<BikeSearchDto> searchCompany(){
        return bikeCompanyRepository.findAll()
                .stream()
                .map(BikeCompanyEntity::getBikeCompanyDto)
                .collect(Collectors.toList());
    }

    //바이크 모델 검색
    public List<BikeSearchDto> searchModel(String company){
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        return bikeCompanyEntity.getBikeModelSet()
                .stream()
                .sorted(Comparator.comparing(BikeModelEntity::getModel))
                .map(BikeModelEntity::getBikeModelDto)
                .collect(Collectors.toList());
    }

    //바이크 연식 검색
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
    //바이크 등록
    @Transactional
    public ResponseEntity<ApiResponse> insertBike(BikeInsertRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(request.getCompany()).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        BikeModelEntity bikeModelEntity = bikeModelRepository.findByModelAndBikeCompany(request.getModel(), bikeCompanyEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_MODEL));
        BikeYearEntity bikeYearEntity = bikeYearRepository.findByYearAndBikeModel(request.getYear(), bikeModelEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_YEAR));

        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);

        BikeEntity bikeEntity = BikeEntity.createBike(user, request.getCompany(), request.getModel(), request.getYear(), request.getMileage(), request.getBikeNickName(), (BikeRole) bikeRoleEnum);
        bikeRepository.save(bikeEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    //TODO : Multipart 추가해야함
    //바이크 수정
    @Transactional
    public void updateBike(BikeUpdateRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(request.getIdx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);
        bikeEntity.updateBike(request, (BikeRole) bikeRoleEnum);
        bikeRepository.save(bikeEntity);
    }

    //바이크 권한 변경
    @Transactional
    public void updateBikeRole(int idx){
        UserEntity user = authService.getUserEntityByAuthentication();

        //이미 대표로 설정된 바이크 있으면 수정
        bikeRepository.findByUserAndBikeRole(user, BikeRole.REPRESENTATIVE).forEach(data -> {
            data.changeBikeRole(BikeRole.NORMAL);
        });

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(idx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bikeEntity.changeBikeRole(BikeRole.REPRESENTATIVE);

        bikeRepository.save(bikeEntity);
    }



    //TODO : 내 바이크 리스트 - 대표바이크 컬럼 없음
    @Transactional
    public List<BikeDto> getMyBikeList(){
        UserEntity user = authService.getUserEntityByAuthentication();

        List<BikeEntity> myBikeList = bikeRepository.findByUser(user);

        return myBikeList
                .stream()
                .map(BikeDto::convertEntityToDto)
                .collect(Collectors.toList());
    }

    // TODO: 내바이크
    public BikeDto getMyBike(Long bikeId){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bike = bikeRepository.findByIdxAndUser(bikeId, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        return BikeDto.convertEntityToDto(bike);
    }

    //TODO : 바이크 추가, 정보수정 요청
}
