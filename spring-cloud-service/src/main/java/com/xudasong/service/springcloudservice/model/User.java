package com.xudasong.service.springcloudservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("null")
public class User {
    @ApiModelProperty("null")
    private String id;

    @ApiModelProperty("null")
    private String username;

    @ApiModelProperty("null")
    private String sex;

    @ApiModelProperty("null")
    private Date birthday;

    @ApiModelProperty("null")
    private String address;
}