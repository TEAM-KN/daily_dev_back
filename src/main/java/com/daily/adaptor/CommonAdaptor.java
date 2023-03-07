package com.daily.adaptor;

import com.daily.domain.contents.domain.Contents;

import java.util.List;

public interface CommonAdaptor {

    List<Contents> getNewContentsFromAdaptor(String requestDate);

}
