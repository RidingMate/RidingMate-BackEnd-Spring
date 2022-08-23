package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.repository.*;
import com.ridingmate.api.repository.bike.BikeRepository;
import com.ridingmate.api.repository.bikeCompany.BikeCompanyRepository;
import com.ridingmate.api.repository.bikeModel.BikeModelRepository;
import com.ridingmate.api.repository.bikeYear.BikeYearRepository;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileService fileService;
    private final FileRepository fileRepository;

    //바이크 제조사 검색
    public List<BikeDto.Request.BikeSearch> searchCompany(){
        return bikeCompanyRepository.findAll()
                .stream()
                .map(BikeCompanyEntity::getBikeCompanyDto)
                .collect(Collectors.toList());
    }

    //바이크 모델 검색
    public List<BikeDto.Request.BikeSearch> searchModel(String company){
        List<BikeModelEntity> bikeModelEntities = bikeModelRepository.searchModel(company);
        return bikeModelEntities
                .stream()
                .map(BikeModelEntity::getBikeModelDto)
                .collect(Collectors.toList());
    }

    //바이크 연식 검색
    public List<BikeDto.Request.BikeSearch> searchYear(String company, String model){
        List<BikeYearEntity> bikeYearEntities = bikeYearRepository.searchYear(company, model);
        return bikeYearEntities.stream()
                .map(BikeYearEntity::getBikeYearDto)
                .collect(Collectors.toList());
    }

    //바이크 등록
    @Transactional
    public void insertBike(BikeDto.Request.BikeInsert request, MultipartFile file) throws Exception {
        UserEntity user = authService.getUserEntityByAuthentication();


        System.out.println(request.getCompany());

        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(request.getCompany()).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        BikeModelEntity bikeModelEntity = bikeModelRepository.findByModelAndBikeCompany(request.getModel(), bikeCompanyEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_MODEL));
        BikeYearEntity bikeYearEntity = bikeYearRepository.findByYearAndBikeModel(request.getYear(), bikeModelEntity).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_YEAR));

        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);

        BikeEntity bikeEntity = new BikeEntity().createBike(user,(BikeRole) bikeRoleEnum, request);
        bikeRepository.save(bikeEntity);

        if(file != null){
            FileEntity fileEntity = fileService.uploadFile(file, user);
            fileEntity.connectBike(bikeEntity);
            fileRepository.save(fileEntity);
        }

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }




    //바이크 수정
    @Transactional
    public void updateBike(BikeDto.Request.BikeUpdate request, MultipartFile file, UserEntity user) throws Exception {
        BikeEntity bikeEntity = bikeRepository.myBikeDetail(request.getIdx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);
        bikeEntity.updateBike(request, (BikeRole) bikeRoleEnum);

        if(file != null){
            if(bikeEntity.getFileEntity() != null){
                fileService.deleteFileEntity(bikeEntity.getFileEntity());
            }
            FileEntity fileEntity = fileService.uploadFile(file, user);
            fileEntity.connectBike(bikeEntity);
            fileRepository.save(fileEntity);
        }
    }

    //바이크 권한 변경
    @Transactional
    public void updateBikeRole(long idx){
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


    //내 바이크 리스트
    public List<BikeDto.Response.MyBike> bikeList(UserEntity user){
        List<BikeEntity> bikeEntities = bikeRepository.myBikeList(user);
        return bikeEntities.stream()
                .map(BikeDto.Response.MyBike::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    //TODO : Multipart 추가해야함
    //바이크 디테일
    public BikeDto.Response.MyBike bikeDetail(long bikeIdx, UserEntity user){
        BikeEntity bikeEntity = bikeRepository.myBikeDetail(bikeIdx, user).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        return BikeDto.Response.MyBike.convertEntityToResponse(bikeEntity);
    }

    //바이크 추가요청
    @Transactional
    public void addBikeRequest(BikeDto.Request.AddBike addBikeRequest, UserEntity user){
        AddBikeEntity addBikeEntity = new AddBikeEntity().convertRequestToEntity(addBikeRequest, user);
        addBikeRepository.save(addBikeEntity);
    }

    //내 바이크 삭제
    @Transactional
    public void deleteBike(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bikeRepository.delete(bikeEntity);
    }

}
