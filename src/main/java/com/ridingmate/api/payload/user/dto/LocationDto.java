package com.ridingmate.api.payload.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationDto {

    private String locationCode;
    private String name;
}
