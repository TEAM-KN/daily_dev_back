package com.daily.global.common.dto;

import lombok.Getter;

@Getter
public enum Constants {
    AUTHORIZATION("Authorization"),
    BEARER("Bearer "),
    ACCESS_TOKEN("access-token"),
    REFRESH_TOKEN("refresh-token");

    private String key;

     Constants(String key) {
        this.key = key;
    }
}
