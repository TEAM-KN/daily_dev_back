package com.news.dev.api.subscriber.service;

public interface SubscribeService {

    public void add(String username) throws Exception;
    public void cancel(String username) throws Exception;
}
