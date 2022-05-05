package com.ridingmate.api.payload.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TradeBoardInsertRequest {

    private String title;
    private String price;
}