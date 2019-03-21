package com.xudasong.service.springcloudservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("null")
public class Employee {
    @ApiModelProperty("null")
    private Integer id;

    @ApiModelProperty("null")
    private String name;

    @ApiModelProperty("null")
    private Integer age;

    @ApiModelProperty("null")
    private Float sal;

    @ApiModelProperty("null")
    private String address;
}