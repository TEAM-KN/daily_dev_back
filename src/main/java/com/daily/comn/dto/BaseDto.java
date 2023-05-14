package com.daily.comn.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDto {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
