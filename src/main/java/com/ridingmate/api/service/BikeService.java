package com.ridingmate.api.service;

import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.payload.BikeCompanyDto;
import com.ridingmate.api.repository.BikeCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeCompanyRepository bikeCompanyRepository;

    public List<BikeCompanyDto> searchCompany(){
        return bikeCompanyRepository.findAll().stream().map(BikeCompanyEntity::getBikeCompanyDto).collect(Collectors.toList());
    }
}
