package com.ridingmate.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.user.dto.LocationDto;
import com.ridingmate.api.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // 도, 광역시 리스트 조회
    public List<LocationEntity> getLocationEntityList() {
        // 하위지역 사용하게되면 변경필요
        return locationRepository.findAllByOrderByLocationCode();
    }

    // 시군구 리스트 조회
    public List<LocationEntity> getSubLocationList(String upperLocationCode) {
        return locationRepository.findByUpperLocationCode(upperLocationCode);
    }

    public LocationEntity getLocation(String locationCode) {
        return locationRepository.findById(locationCode).orElseThrow(
                () -> new CustomException(ResponseCode.NOT_FOUND_LOCATION));
    }

    public List<LocationDto> getLocationList() {
        return getLocationEntityList().stream()
                                      .map(location -> new LocationDto(location.getLocationCode(),
                                                                 location.getName()))
                                      .collect(Collectors.toList());
    }
}
