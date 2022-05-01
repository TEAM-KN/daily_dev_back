package com.news.dev.api.contents.dto;

import lombok.Getter;

@Getter
public enum DateType {
    Jan( "1"),
    Feb("2"),
    Mar( "3"),
    Apr( "4"),
    May( "5"),
    Jun( "6"),
    Jul( "7"),
    Aug( "8"),
    Sep( "9"),
    Oct( "10"),
    Nov( "11"),
    Dec( "12");

    private String month;

    DateType(String month) {
        this.month = month;
    }

}
