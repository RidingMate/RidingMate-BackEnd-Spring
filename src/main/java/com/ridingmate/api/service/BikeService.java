package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.AddBikeRequest;
import com.ridingmate.api.payload.user.request.BikeInsertRequest;
import com.ridingmate.api.payload.user.dto.BikeSearchDto;
import com.ridingmate.api.payload.user.request.BikeUpdateRequest;
import com.ridingmate.api.payload.user.response.MyBikeResponse;
import com.ridingmate.api.repository.*;
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
    private final AddBikeRepository addBikeRepository;

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

        BikeEntity bikeEntity = BikeEntity.createBike(user, request.getCompany(), request.getModel(), request.getYear(), request.getMileage(), request.getBikeNickName(), (BikeRole) bikeRoleEnum, request.getDateOfPurchase());
        bikeRepository.save(bikeEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    //TODO : Multipart 추가해야함
    //바이크 수정
    @Transactional
    public ResponseEntity<ApiResponse> updateBike(BikeUpdateRequest request){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(request.getIdx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);
        bikeEntity.updateBike(request, (BikeRole) bikeRoleEnum);
        bikeRepository.save(bikeEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    //바이크 권한 변경
    @Transactional
    public ResponseEntity<ApiResponse> updateBikeRole(long idx){
        UserEntity user = authService.getUserEntityByAuthentication();

        //이미 대표로 설정된 바이크 있으면 수정
        bikeRepository.findByUserAndBikeRole(user, BikeRole.REPRESENTATIVE).forEach(data -> {
            data.changeBikeRole(BikeRole.NORMAL);
        });

        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(idx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bikeEntity.changeBikeRole(BikeRole.REPRESENTATIVE);

        bikeRepository.save(bikeEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }



    //내 바이크 리스트 - 대표바이크 컬럼 없음
    public List<MyBikeResponse> bikeList(){
        UserEntity user = authService.getUserEntityByAuthentication();
        List<BikeEntity> bikeEntities = bikeRepository.findByUserOrderByBikeRole(user);
        return bikeEntities.stream().map(bikeEntity ->
                new MyBikeResponse().convertEntityToResponse(bikeEntity))
                .collect(Collectors.toList());
    }

    //TODO : Multipart 추가해야함
    //바이크 디테일
    public MyBikeResponse bikeDetail(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        return new MyBikeResponse().convertEntityToResponse(bikeEntity);
    }

    //바이크 추가요청
    @Transactional
    public ResponseEntity<ApiResponse> addBikeRequest(AddBikeRequest addBikeRequest){
        UserEntity user = authService.getUserEntityByAuthentication();
        AddBikeEntity addBikeEntity = new AddBikeEntity().convertRequestToEntity(addBikeRequest, user);
        addBikeRepository.save(addBikeEntity);

        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

}
