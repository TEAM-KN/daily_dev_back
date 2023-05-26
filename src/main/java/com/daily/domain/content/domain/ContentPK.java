package com.daily.domain.content.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ContentPK implements Serializable {
    private Long contentId;
    private String siteCode;
}
