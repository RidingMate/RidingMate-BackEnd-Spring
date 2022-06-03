package com.ridingmate.api.controller.user;

import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
import com.ridingmate.api.service.MaintenanceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    정비 관련 컨트롤러 등록 컨트롤러

    -리스트 : 바이크 별칭, 제조사, 모델명 디폴트
        연단위로 필터링
        연단위 정비 카운트, 총 가격 표출
        리스트는 title, content, 가격, 날짜 표출
    -등록 : title, content(이미지 x), 정비항목(list) 추가하면서 작성 가능하게, 날짜, 가격 입력받아서 저장, 이미지 최대 8
    -수정 : 등록과 동일하게
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/list/{bike_idx}/{year}")
    @ApiOperation(value="정비기록 리스트 조회 - 연도별")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "Long", required = true),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<MaintenanceResponse> maintenanceList(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bike_idx") Long bike_idx,
            @PathVariable("year") int year
    ) {
        return maintenanceService.getMaintenanceList(bike_idx, year);
    }

    @GetMapping("/detail/{bike_idx}/{maintenance_idx}")
    @ApiOperation(value="정비기록 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
            @ApiImplicitParam(name = "bike_idx", value = "bike_idx", dataType = "Long", required = true),
            @ApiImplicitParam(name = "maintenance_idx", value = "maintenance_idx", dataType = "Long", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public MaintenanceResponse getMaintenanceDetail(
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("bike_idx") Long bike_idx,
            @PathVariable("maintenance_idx") Long maintenance_idx
    ){
        return maintenanceService.getMaintenanceDetail(bike_idx,maintenance_idx);
    }

    @PostMapping("/insert")
    @ApiOperation(value="정비 기록 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> insertMaintenance(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody MaintenanceInsertRequest request){
        return maintenanceService.insertMaintenance(request);
    }

    @PutMapping("/update")
    @ApiOperation(value="정비 기록 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "user 토큰", defaultValue = "null", dataType = "String", required = true),
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> updateMaintenance(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody MaintenanceUpdateRequest request){

        return maintenanceService.updateMaintenance(request);
    }

}
