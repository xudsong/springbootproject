package com.xudasong.service.springcloudservice.service;


public interface ITestService {

    public String test(String name);

    public void sendSms(Object object) throws InterruptedException;

}
