package com.ridingmate.api.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BikeSearchDto {
    private String content;
}
