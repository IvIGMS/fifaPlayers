package com.ivanfrias.fifaPlayers.Models.enums;

import lombok.Getter;

@Getter
public enum GroupByEnum {
    NATIONALITY("nationality"),
    AGE("age"),
    OVERALL_RATING("overall_rating"),
    POTENTIAL("potential");

    private final String value;

    GroupByEnum(String value){
        this.value=value;
    }

}
