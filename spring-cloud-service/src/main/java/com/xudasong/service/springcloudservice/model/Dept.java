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
public class Dept {
    @ApiModelProperty("null")
    private Integer id;

    @ApiModelProperty("null")
    private String dept;

    @ApiModelProperty("null")
    private Integer facId;
}