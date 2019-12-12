package com.xudasong.service.springcloudservice.req;

import lombok.Data;

@Data
public class JobInfoReq {

    private String jobClassName;

    private String jobGroupName;

    private String cronExpression;

    private String jobType;

    private Integer timeType;


//    public String getJobClassName() {
//        return "com.example.quartz.jobs."+jobClassName.trim();
//    }

}
