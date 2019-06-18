package com.xudasong.service.springcloudservice.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
public class MongodbTest {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("price")
    private Double price;

}
