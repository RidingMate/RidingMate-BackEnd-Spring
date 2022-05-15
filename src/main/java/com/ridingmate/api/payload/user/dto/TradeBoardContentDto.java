package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.TradeBoardEntity;
import lombok.Getter;

@Getter
public class TradeBoardContentDto {

    private String title;
    private String modelName;
    private String company;
    private int mileage;
    private String cc;
    private int year;
    private String price;

    public TradeBoardContentDto(TradeBoardEntity tradeBoard) {
        title = tradeBoard.getTitle();
        modelName = tradeBoard.getModelName();
        company = tradeBoard.getCompany();
        mileage = tradeBoard.getMileage();
        cc = tradeBoard.getCc();
        year = tradeBoard.getYear();
        price = tradeBoard.getPrice();
    }
}
