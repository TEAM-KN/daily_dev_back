package com.daily.global.common.dto;

import lombok.Getter;

@Getter
public enum DateType {
    Jan( "01"),
    Feb("02"),
    Mar( "03"),
    Apr( "04"),
    May( "05"),
    Jun( "06"),
    Jul( "07"),
    Aug( "08"),
    Sep( "09"),
    Oct( "10"),
    Nov( "11"),
    Dec( "12");

    private String month;

    DateType(String month) {
        this.month = month;
    }

    public static String parseMonth(final String month) {
        if (month.length() == 1 && month.matches("[0-9]") && Integer.parseInt(month) >= 0 && Integer.parseInt(month) <= 9) {
            return "0" + month;
        }

        return month;
    }

}
