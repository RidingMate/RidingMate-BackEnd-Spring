package com.ridingmate.api.payload.user.dto;

import com.ridingmate.api.entity.TradeBoardEntity;
import lombok.Getter;

@Getter
public class TradeBoardDto {

    private Long id;
    private String title;
    private String modelName;
    private String company;
    private String mileage;
    private String cc;
    private String year;
    private String price;

    public TradeBoardDto(TradeBoardEntity tradeBoard) {
        id = tradeBoard.getIdx();
        title = tradeBoard.getTitle();
        modelName = tradeBoard.getModelName();
        company = tradeBoard.getCompany();
        mileage = tradeBoard.getMileage();
        cc = tradeBoard.getCc();
        year = tradeBoard.getYear();
        price = tradeBoard.getPrice();
    }
}
