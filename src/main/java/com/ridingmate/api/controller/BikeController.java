package com.ridingmate.api.controller;

import com.ridingmate.api.payload.user.dto.BikeDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bike")
public class BikeController {

    private final BikeService bikeService;


    @GetMapping("/search/company")
    @ApiOperation(value = "바이크 등록 - 제조사 검색")
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
    @ApiOperation(value = "바이크 등록 - 모델 검색")
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
    @ApiOperation(value = "바이크 등록 - 연식 검색")
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

    // TODO: 내 바이크 리스트 불러오기
    @GetMapping("/myBike/list")
    @ApiOperation(value = "내 바이크 리스트 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<BikeDto> getMyBikeList(
            @RequestHeader(value = "Authorization") String token
    ){
        return bikeService.getMyBikeList();
    }

    // TODO: 내 바이크 하나 불러오기
    @GetMapping("/myBike/{bikeId}")
    @ApiOperation(value = "내 바이크 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public BikeDto getMyBike(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bikeId") Long idx
    ){
        return bikeService.getMyBike(idx);
    }


}
