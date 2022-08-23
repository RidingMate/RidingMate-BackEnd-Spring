package com.ridingmate.api.repository.bikeYear;

import com.ridingmate.api.entity.BikeYearEntity;

import java.util.List;

public interface BikeYearCustomRepository {
    List<BikeYearEntity> searchYear(String company, String model);
}
