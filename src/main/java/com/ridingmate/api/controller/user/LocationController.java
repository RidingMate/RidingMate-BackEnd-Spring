package com.ridingmate.api.controller.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.payload.user.dto.LocationDto;
import com.ridingmate.api.payload.common.ResponseDto;
import com.ridingmate.api.service.LocationService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/list")
    @ApiOperation("도, 광역시 조회")
    public ResponseDto<List<LocationDto>> getLocationList() {
        return ResponseDto.<List<LocationDto>>builder()
                          .response(locationService.getLocationList())
                          .build();
    }

}
