package com.ridingmate.api.service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.entity.value.BikeRole;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.repository.*;
import com.ridingmate.api.service.common.AuthService;
import com.ridingmate.api.service.common.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        BikeCompanyEntity bikeCompanyEntity = bikeCompanyRepository.findByCompany(company).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_COMPANY));
        return bikeCompanyEntity.getBikeModelSet()
                .stream()
                .sorted(Comparator.comparing(BikeModelEntity::getModel))
                .map(BikeModelEntity::getBikeModelDto)
                .collect(Collectors.toList());
    }

    //바이크 연식 검색
    public List<BikeDto.Request.BikeSearch> searchYear(String company, String model){
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

    //TODO : Multipart 추가해야함
    //바이크 수정
    @Transactional
    public void updateBike(BikeDto.Request.BikeUpdate request, MultipartFile file) throws Exception {
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(request.getIdx(), user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));
        Enum<BikeRole> bikeRoleEnum = BikeRole.checkBikeRole(request.getBikeRole(), user);
        bikeEntity.updateBike(request, (BikeRole) bikeRoleEnum);
        bikeRepository.save(bikeEntity);

        if(file != null){
            if(bikeEntity.getFileEntity() != null){
                fileService.deleteFileEntity(bikeEntity.getFileEntity());
            }
            FileEntity fileEntity = fileService.uploadFile(file, user);
            fileEntity.connectBike(bikeEntity);
            fileRepository.save(fileEntity);
        }

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
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

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }



    //내 바이크 리스트
    public List<BikeDto.Response.MyBike> bikeList(){
        UserEntity user = authService.getUserEntityByAuthentication();
        List<BikeEntity> bikeEntities = bikeRepository.findByUserOrderByBikeRole(user);
        return bikeEntities.stream().map(bikeEntity -> BikeDto.Response.MyBike.convertEntityToResponse(bikeEntity)).collect(Collectors.toList());

    }

    //TODO : Multipart 추가해야함
    //바이크 디테일
    public BikeDto.Response.MyBike bikeDetail(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        return BikeDto.Response.MyBike.convertEntityToResponse(bikeEntity);
    }

    //바이크 추가요청
    @Transactional
    public void addBikeRequest(BikeDto.Request.AddBike addBikeRequest){
        UserEntity user = authService.getUserEntityByAuthentication();
        AddBikeEntity addBikeEntity = new AddBikeEntity().convertRequestToEntity(addBikeRequest, user);
        addBikeRepository.save(addBikeEntity);

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

    //내 바이크 삭제
    @Transactional
    public void deleteBike(long bikeIdx){
        UserEntity user = authService.getUserEntityByAuthentication();
        BikeEntity bikeEntity = bikeRepository.findByIdxAndUser(bikeIdx, user).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_BIKE));

        bikeRepository.delete(bikeEntity);

//        return ResponseEntity.ok(new ApiResponse(ResponseCode.SUCCESS));
    }

}
