package com.ivanfrias.fifaPlayers.Models.enums;

import lombok.Getter;

@Getter
public enum OrderByEnum {
    NATIONALITY("nationality"),
    AGE("age"),
    OVERALL_RATING("overall_rating"),
    ID("id"),
    BIRTHDATE("birth_date"),
    VALUE_EURO("value_euro");


    private final String value;

    OrderByEnum(String value){
        this.value=value;
    }
}
