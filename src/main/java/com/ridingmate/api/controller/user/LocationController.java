package com.ridingmate.api.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ridingmate.api.payload.user.dto.LocationDto;
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
    public List<LocationDto> getLocationList() {
        return locationService.getLocationList()
                              .stream()
                              .map(location -> new LocationDto(location.getLocationCode(),
                                                               location.getName()))
                              .collect(Collectors.toList());
    }

    @GetMapping("/list/sub")
    @ApiOperation("시군구 조회")
    public List<LocationDto> getSubLocationList(@RequestParam String upperLocationCode) {
        return locationService.getSubLocationList(upperLocationCode)
                              .stream()
                              .map(location -> new LocationDto(location.getLocationCode(),
                                                               location.getName()))
                              .collect(Collectors.toList());
    }
}
