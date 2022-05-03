package com.ridingmate.api.payload;

import com.ridingmate.api.entity.TradeBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TradeBoardDto {

    private Long id;
    private String title;
    private String modelName;
    private String company;
    private String mileage;
    private String cc;
    private String year;
    private String price;

    public static TradeBoardDto convertEntityToDto(TradeBoardEntity tradeBoard) {
        return new TradeBoardDto(tradeBoard.getIdx(),
                tradeBoard.getTitle(),
                tradeBoard.getModelName(),
                tradeBoard.getCompany(),
                tradeBoard.getMileage(),
                tradeBoard.getCc(),
                tradeBoard.getYear(),
                tradeBoard.getPrice());
    }
}
