package com.xudasong.service.springcloudservice.service;

import com.github.pagehelper.PageInfo;
import com.xudasong.service.springcloudservice.model.JobAndTrigger;

public interface IJobAndTriggerService {

    PageInfo<JobAndTrigger> getJobAndTriggerDetails(Integer pageNum, Integer pageSize);

}
