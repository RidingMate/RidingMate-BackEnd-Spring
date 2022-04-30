package com.ridingmate.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BikeSearchDto {
    private String content;
}
