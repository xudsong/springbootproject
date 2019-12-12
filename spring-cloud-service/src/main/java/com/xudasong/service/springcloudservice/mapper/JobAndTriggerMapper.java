package com.xudasong.service.springcloudservice.mapper;

import com.xudasong.service.springcloudservice.model.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper {
    List<JobAndTrigger> getJobAndTriggerDetails();
}
