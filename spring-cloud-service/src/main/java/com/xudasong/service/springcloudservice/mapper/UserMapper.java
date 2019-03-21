package com.xudasong.service.springcloudservice.mapper;

import com.xudasong.service.springcloudservice.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
/* Created by Mybatis Generator on 2019/03/21
*/
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}