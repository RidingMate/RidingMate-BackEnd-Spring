package com.ridingmate.api.controller.user;

import com.ridingmate.api.annotation.CurrentUser;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.payload.user.dto.FuelDto;
import com.ridingmate.api.security.UserPrincipal;
import com.ridingmate.api.service.FuelService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/*
    연비 관련 컨트롤러 등록 컨트롤러

    -등록 : 날짜, 주유량, 현재주행거리, 주유가격 입력 받아서 저장
        -> 현재 주행거리 나의 바이크 주행거리로 최신화
        -> 연비 수학계산해서 처리
    -리스트 : 주유량, 주유가격, 최근 연비 표출
    -초기화 : 주유기록 db는 남겨놓은 상태로 주행거리 초기 셋팅을 입력받음 -> 다시 연비 측정 시작

*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/fuel")
public class FuelController {

    private final FuelService fuelService;


    @GetMapping("/list/{bike_idx}")
    @ApiOperation(value = "내 바이크 연비 리스트")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto<FuelDto.Response.FuelList> myBikeFuelList(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable("bike_idx") Long bike_idx
    ){
        return ResponseDto.<FuelDto.Response.FuelList>builder()
                .response(fuelService.list(bike_idx, user.getUser()))
                .build();
//        return fuelService.list(bike_idx);
    }

    @PutMapping("/add/{bike_idx}")
    @ApiOperation(value = "주유 기록 추가")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto myBikeFuelList(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @RequestBody FuelDto.Request.AddFuel addFuelRequest
            ){
        fuelService.addFuel(addFuelRequest, user.getUser());
        return ResponseDto.builder().build();
    }

    @GetMapping("/reset/{bike_idx}")
    @ApiOperation(value = "내 바이크 연비 초기화")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseDto resetFuel(
            @ApiIgnore @CurrentUser UserPrincipal user,
            @PathVariable("bike_idx") Long bike_idx
    ){
        fuelService.reset(bike_idx, user.getUser());
        return ResponseDto.builder().build();
    }

}
