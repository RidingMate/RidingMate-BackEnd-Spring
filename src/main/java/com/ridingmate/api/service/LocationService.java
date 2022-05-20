package com.ridingmate.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ridingmate.api.entity.LocationEntity;
import com.ridingmate.api.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // 도, 광역시 리스트 조회
    public List<LocationEntity> getLocationList() {
        return locationRepository.findByLocationCodeEndsWithOrderByLocationCode("00000000");
    }

    // 시군구 리스트 조회
    public List<LocationEntity> getSubLocationList(String upperLocationCode) {
        return locationRepository.findByUpperLocationCode(upperLocationCode);
    }
}
