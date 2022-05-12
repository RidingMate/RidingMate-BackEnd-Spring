package com.ridingmate.api.controller.user;

import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.BikeInsertRequest;
import com.ridingmate.api.payload.user.dto.BikeSearchDto;
import com.ridingmate.api.payload.user.request.BikeUpdateRequest;
import com.ridingmate.api.service.BikeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    바이크 등록 컨트롤러

    -제조사 검색
    -모델명 검색
    -년식 검색
    -바이크 등록(바이크 별칭, 누적 주행거리, 구매일자, 이미지 1, 대표바이크 설정)
    -정보 추가 요청 -> 어드민으로 데이터 넘겨서 직접입력 해서 다시 검색에 사용할 수 있도록
    -대표 바이크 변경
    -내 바이크 리스트 (리스트당 이미지, 누적주행거리, 평균연비, 구입일자, 주유기록카운트, 정비기록 카운트, 바이크 별칭, 대표바이크 유무)
    -바이크 디테일(별칭, 제조가, 모델명, 누적 주행거리, 평균연비, 주유기록카운트(월 단위로 필터링), 주유기록 리스트 받아서 표출)
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bike")
public class BikeController {

    private final BikeService bikeService;


    @GetMapping("/search/company")
    @ApiOperation(value = "제조사 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BikeSearchDto> searchCompany(
            @RequestHeader(value = "Authorization") String token
    ){
        return bikeService.searchCompany();
    }

    @GetMapping("/search/model")
    @ApiOperation(value = "모델 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BikeSearchDto> searchModel(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "company") String company
    ){
        return bikeService.searchModel(company);
    }

    @GetMapping("/search/year")
    @ApiOperation(value = "연식 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "model", value = "model name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BikeSearchDto> searchYear(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "company") String company,
            @RequestParam(value = "model") String model
    ){
        return bikeService.searchYear(company, model);
    }

    @PostMapping
    @ApiOperation(value = "바이크 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> insertBike(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody BikeInsertRequest bikeInsertRequest
            ){
        return bikeService.insertBike(bikeInsertRequest);
    }

    @PutMapping
    @ApiOperation(value = "바이크 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateBike(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody BikeUpdateRequest request
            ){
        bikeService.updateBike(request);
    }

    @GetMapping("/role")
    @ApiOperation(value = "대표 바이크 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public void updateBikeRole(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "bike_idx") int idx
    ){
        bikeService.updateBikeRole(idx);
    }

}
