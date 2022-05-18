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
    public List<String> getLocationList() {
        List<LocationEntity> locationList = locationRepository.findByLocationCodeEndsWith("00000000");
        return locationList.stream().map(LocationEntity::getName).collect(Collectors.toList());
    }

    // 시군구 리스트 조회
    public List<String> getSubLocationList(String upperLocationCode) {
        List<LocationEntity> locationList = locationRepository.findByUpperLocationCode(upperLocationCode);
        return locationList.stream().map(LocationEntity::getName).collect(Collectors.toList());
    }
}
