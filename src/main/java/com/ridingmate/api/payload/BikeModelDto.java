package com.ridingmate.api.payload;

import lombok.Builder;

@Builder
public class BikeModelDto {
    private long idx;
    private String model;
}
