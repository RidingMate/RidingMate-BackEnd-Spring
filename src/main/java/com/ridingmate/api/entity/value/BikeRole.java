package com.ridingmate.api.entity.value;

import com.ridingmate.api.entity.UserEntity;

import java.util.Locale;

public enum BikeRole {
    REPRESENTATIVE, NORMAL;

    public static BikeRole checkBikeRole(String bikeRole, UserEntity user){
        if(bikeRole.toUpperCase(Locale.ROOT).equals(BikeRole.REPRESENTATIVE.toString().toUpperCase(Locale.ROOT))){
            user.getBike().forEach(data->{
                if(data.checkBikeRole()){
                    data.changeBikeRole(BikeRole.NORMAL);
                }
            });
            return BikeRole.REPRESENTATIVE;
        }else{
            return BikeRole.NORMAL;
        }
    }
}
