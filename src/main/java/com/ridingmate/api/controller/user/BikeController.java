package com.ridingmate.api.controller.user;

import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.service.BikeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
    바이크 등록 컨트롤러

    <완료>
    -제조사 검색
    -모델명 검색
    -년식 검색
    -대표 바이크 변경
    -정보 추가 요청 -> 어드민으로 데이터 넘겨서 직접입력 해서 다시 검색에 사용할 수 있도록 -> 어드민쪽 작업해야함
    -내 바이크 리스트 (리스트당 이미지, 누적주행거리, 평균연비, 구입일자, 주유기록카운트, 정비기록 카운트, 바이크 별칭, 대표바이크 유무)
    -바이크 디테일(별칭, 제조가, 모델명, 누적 주행거리, 평균연비, 주유기록카운트(월 단위로 필터링), 주유기록 리스트 받아서 표출)

    <미완성>
    -바이크 등록(바이크 별칭, 누적 주행거리, 구매일자, 이미지 1, 대표바이크 설정)


    <진행 전>
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
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchCompany(
            @RequestHeader(value = "Authorization") String token
    ){
        return ResponseDto.<List<BikeDto.Request.BikeSearch>>builder()
                .response(bikeService.searchCompany())
                .build();
//        return bikeService.searchCompany();
    }

    @GetMapping("/search/model")
    @ApiOperation(value = "모델 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchModel(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "company") String company
    ){
        return ResponseDto.<List<BikeDto.Request.BikeSearch>>builder()
                .response(bikeService.searchModel(company))
                .build();
//        return bikeService.searchModel(company);
    }

    @GetMapping("/search/year")
    @ApiOperation(value = "연식 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "model", value = "model name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchYear(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(value = "company") String company,
            @RequestParam(value = "model") String model
    ){
        return ResponseDto.<List<BikeDto.Request.BikeSearch>>builder()
                .response(bikeService.searchYear(company, model))
                .build();
//        return bikeService.searchYear(company, model);
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value = "바이크 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto insertBike(
            @RequestHeader(value = "Authorization") String token,
            @ModelAttribute BikeDto.Request.BikeInsert bikeInsertRequest,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws Exception {
        bikeService.insertBike(bikeInsertRequest, file);
        return ResponseDto.builder().build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "바이크 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto updateBike(
            @RequestHeader(value = "Authorization") String token,
            @ModelAttribute BikeDto.Request.BikeUpdate request,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws Exception {
        bikeService.updateBike(request, file);
        return ResponseDto.builder().build();
    }

    @GetMapping("/role/{bike_idx}")
    @ApiOperation(value = "대표 바이크 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto updateBikeRole(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bike_idx") Long bike_idx
    ){
        bikeService.updateBikeRole(bike_idx);
        return ResponseDto.builder().build();
    }

    @GetMapping("/list")
    @ApiOperation(value = "내 바이크 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Response.MyBike>> myBikeList(
            @RequestHeader(value = "Authorization") String token
    ){
        return ResponseDto.<List<BikeDto.Response.MyBike>>builder()
                .response(bikeService.bikeList())
                .build();
//        return bikeService.bikeList();
    }

    @GetMapping("/detail/{bike_idx}")
    @ApiOperation(value = "내 바이크 디테일")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<BikeDto.Response.MyBike> bikeDetail(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bike_idx") Long bike_idx
    ){
        return ResponseDto.<BikeDto.Response.MyBike>builder()
                .response(bikeService.bikeDetail(bike_idx))
                .build();
//        return bikeService.bikeDetail(bike_idx);
    }

    @PutMapping("/add")
    @ApiOperation(value = "바이크 추가 요청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto addBikeRequest(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody BikeDto.Request.AddBike addBikeRequest
    ){
        bikeService.addBikeRequest(addBikeRequest);
        return ResponseDto.builder().build();
    }

    @DeleteMapping("/delete/{bike_idx}")
    @ApiOperation(value = "바이크 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto deleteBike(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bike_idx") Long bike_idx
    ){
        bikeService.deleteBike(bike_idx);
        return ResponseDto.builder().build();
    }


}
