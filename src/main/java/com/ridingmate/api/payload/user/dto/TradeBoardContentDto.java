package com.ridingmate.api.payload.user.dto;

import java.time.format.DateTimeFormatter;

import com.ridingmate.api.entity.TradeBoardEntity;
import lombok.Getter;

@Getter
public class TradeBoardContentDto {

    private String title;
    private String modelName;
    private String company;
    private int mileage;
    private int cc;
    private int year;
    private int price;
    private String location;
    private String dateOfPurchase;

    public TradeBoardContentDto(TradeBoardEntity tradeBoard) {
        title = tradeBoard.getTitle();
        modelName = tradeBoard.getModelName();
        company = tradeBoard.getCompany();
        mileage = tradeBoard.getMileage();
        cc = tradeBoard.getCc();
        year = tradeBoard.getYear();
        price = tradeBoard.getPrice();
        location = tradeBoard.getLocation().getName();
        dateOfPurchase = tradeBoard.getDateOfPurchase().format(DateTimeFormatter.ofPattern("YYYY-MM"));
    }
}
