package com.ridingmate.api.payload;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BikeUpdateRequest extends BikeInsertRequest{

    private long idx;

}
