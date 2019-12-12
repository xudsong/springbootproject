package com.xudasong.service.springcloudservice.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class JobAndTrigger {

    private String JobName;
    private String JobGroup;
    private String JobClassName;
    private String TriggerName;
    private String TriggerGroup;
    private BigInteger RepeatInterval;
    private BigInteger TimesTriggered;
    private String CronExpression;
    private String TimeZoneId;

}
