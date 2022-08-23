package com.ridingmate.api.controller.user;

import com.ridingmate.api.annotation.CurrentUser;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.BikeDto;
import com.ridingmate.api.security.UserPrincipal;
import com.ridingmate.api.service.BikeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

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
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchCompany(
            @ApiIgnore @CurrentUser UserPrincipal user
    ){
        return ResponseDto.<List<BikeDto.Request.BikeSearch>>builder()
                .response(bikeService.searchCompany())
                .build();
//        return bikeService.searchCompany();
    }

    @GetMapping("/search/model")
    @ApiOperation(value = "모델 검색")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchModel(
            @ApiIgnore @CurrentUser UserPrincipal user,
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
            @ApiImplicitParam(name = "company", value = "company name", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "model", value = "model name", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Request.BikeSearch>> searchYear(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @RequestParam(value = "company") String company,
            @RequestParam(value = "model") String model
    ){
        return ResponseDto.<List<BikeDto.Request.BikeSearch>>builder()
                .response(bikeService.searchYear(company, model))
                .build();
    }

    @PostMapping(value = "/insert")
    @ApiOperation(value = "바이크 등록")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto insertBike(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @ModelAttribute BikeDto.Request.BikeInsert bikeInsertRequest,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws Exception {
        bikeService.insertBike(bikeInsertRequest, file);
        return ResponseDto.builder().build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "바이크 수정")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto updateBike(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @ModelAttribute BikeDto.Request.BikeUpdate request,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws Exception {
        bikeService.updateBike(request, file, user.getUser());
        return ResponseDto.builder().build();
    }

    @GetMapping("/role/{bike_idx}")
    @ApiOperation(value = "대표 바이크 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto updateBikeRole(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable("bike_idx") Long bike_idx
    ){
        bikeService.updateBikeRole(bike_idx);
        return ResponseDto.builder().build();
    }

    @GetMapping("/list")
    @ApiOperation(value = "내 바이크 리스트")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<List<BikeDto.Response.MyBike>> myBikeList(
            @ApiIgnore @CurrentUser UserPrincipal user
    ){
        return ResponseDto.<List<BikeDto.Response.MyBike>>builder()
                .response(bikeService.bikeList(user.getUser()))
                .build();
    }

    @GetMapping("/detail/{bike_idx}")
    @ApiOperation(value = "내 바이크 디테일")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<BikeDto.Response.MyBike> bikeDetail(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable("bike_idx") Long bike_idx
    ){
        return ResponseDto.<BikeDto.Response.MyBike>builder()
                .response(bikeService.bikeDetail(bike_idx, user.getUser()))
                .build();
    }

    @PutMapping("/add")
    @ApiOperation(value = "바이크 추가 요청")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto addBikeRequest(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @RequestBody BikeDto.Request.AddBike addBikeRequest
    ){
        bikeService.addBikeRequest(addBikeRequest, user.getUser());
        return ResponseDto.builder().build();
    }

    @DeleteMapping("/delete/{bike_idx}")
    @ApiOperation(value = "바이크 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto deleteBike(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable("bike_idx") Long bike_idx
    ){
        bikeService.deleteBike(bike_idx);
        return ResponseDto.builder().build();
    }


}
