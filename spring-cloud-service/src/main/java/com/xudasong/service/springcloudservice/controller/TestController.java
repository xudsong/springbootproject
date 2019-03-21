package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import com.xudasong.service.springcloudservice.dto.TokenInfoDto;
import com.xudasong.service.springcloudservice.service.ITestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Api(tags = "测试服务")
@Validated
public class TestController extends BaseController{

    @Autowired
    private ITestService testService;

    @ApiOperation(value = "测试")
    @PostMapping(value = "/api/auth/test")
    public CommonResponse<String> test(@Valid @RequestParam @NotBlank(message = "name不能为空") String name){
        TokenInfoDto tokenInfoDto = getTokenInfo();
        String result = testService.test(name);
        return CommonResponse.success(result);
    }

}
