package com.ridingmate.api.payload.user.request;

import lombok.Getter;

@Getter
public class TradeBoardRequest {
    private String title;
    private String price;
    private String company;
    private String modelName;
    private double fuelEfficiency;
    private String cc;
    private String year;
    private String mileage;
}
