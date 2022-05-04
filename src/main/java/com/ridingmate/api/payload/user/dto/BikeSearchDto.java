package com.ridingmate.api.payload.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BikeSearchDto {
    private String content;
}
