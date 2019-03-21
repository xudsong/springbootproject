package com.xudasong.service.springcloudservice.mapper;

import com.xudasong.service.springcloudservice.model.Dept;
import org.apache.ibatis.annotations.Mapper;

/**
/* Created by Mybatis Generator on 2019/03/21
*/
@Mapper
public interface DeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
}