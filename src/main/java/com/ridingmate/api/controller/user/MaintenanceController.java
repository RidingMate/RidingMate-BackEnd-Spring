package com.ridingmate.api.controller.user;

import com.ridingmate.api.payload.common.ApiResponse;
import com.ridingmate.api.payload.user.request.MaintenanceInsertRequest;
import com.ridingmate.api.payload.user.request.MaintenanceUpdateRequest;
import com.ridingmate.api.payload.user.response.MaintenanceResponse;
import com.ridingmate.api.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{bike_id}/list")
    public List<MaintenanceResponse> maintenanceList(
            @PathVariable("bike_id") Long bike_id
    ) {

        // TODO: 연단위로 필터링
        return maintenanceService.getMaintenanceList(bike_id);
    }

    // TODO: 정비기록 하나 보이기
    @GetMapping("/{bike_id}/detail/{maintenance_id}")
    public MaintenanceResponse getMaintenanceDetail(
            @PathVariable("bike_id") Long bike_id,
            @PathVariable("maintenance_id") Long maintenance_id
    ){
        return maintenanceService.getMaintenanceDetail(bike_id,maintenance_id);
    }

    @PostMapping("/{bike_id}/insert")
    public ResponseEntity<ApiResponse> insertMaintenance(
            @PathVariable("bike_id") Long bike_id,
            @RequestBody MaintenanceInsertRequest request){
        return maintenanceService.insertMaintenance(bike_id, request);
    }

    //TODO: 수정
    @PutMapping("/{bike_id}/update/{maintenance_id}")
    public ResponseEntity<ApiResponse> updateMaintenance(
            @PathVariable("bike_id") Long bike_id,
            @PathVariable("maintenance_id") Long maintenance_id,
            @RequestBody MaintenanceUpdateRequest request){

        return maintenanceService.updateMaintenance(bike_id, maintenance_id, request);
    }

}
