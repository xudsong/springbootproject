package com.xudasong.service.springcloudservice.mapper;

import com.xudasong.service.springcloudservice.model.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
/* Created by Mybatis Generator on 2019/03/21
*/
@Mapper
public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}