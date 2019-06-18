package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import com.xudasong.service.springcloudservice.model.MongodbTest;
import com.xudasong.service.springcloudservice.service.IMongodbTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongodbTestController {

    @Autowired
    private IMongodbTestService mongodbTestService;

    @ApiOperation(value = "保存信息到mongodb")
    @PostMapping(value = "/mongodb/saveTest", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse saveTest(){
        MongodbTest mongodbTest = new MongodbTest();
        mongodbTest.setId(1);
        mongodbTest.setName("《C语言》");
        mongodbTest.setPrice(32.5);
        mongodbTestService.saveTest(mongodbTest);
        return CommonResponse.success();
    }

    @ApiOperation(value = "到mongodb查找信息")
    @GetMapping(value = "/mongodb/findTest")
    public CommonResponse findTest(){

        return CommonResponse.success(mongodbTestService.findTest("id",1));

    }

    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/mongodb/updateTest")
    public CommonResponse updateTest(){
        MongodbTest mongodbTest = new MongodbTest();
        mongodbTest.setId(1);
        mongodbTest.setName("《小学语文课程》");
        mongodbTest.setPrice(44.0);
        mongodbTestService.updateTest(mongodbTest);
        return CommonResponse.success();
    }

    @ApiOperation(value = "删除对象")
    @GetMapping(value = "/mongodb/deleteTest")
    public CommonResponse deleteTest(){
        mongodbTestService.deleteTes("id", 1);
        return CommonResponse.success();
    }

}
