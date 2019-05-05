package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import com.xudasong.service.springcloudservice.dto.TokenInfoDto;
import com.xudasong.service.springcloudservice.mapper.UserMapper;
import com.xudasong.service.springcloudservice.model.User;
import com.xudasong.service.springcloudservice.service.ITestService;
import com.xudasong.service.springcloudservice.util.ExcelUtils;
import com.xudasong.service.springcloudservice.util.SheetData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Api(tags = "测试服务")
@Validated
public class TestController extends BaseController{

    @Autowired
    private ITestService testService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "测试")
    @PostMapping(value = "/api/release/test")
    public CommonResponse<String> test(@Valid @RequestParam @NotBlank(message = "name不能为空") String name){
        //TokenInfoDto tokenInfoDto = getTokenInfo();
        String result = testService.test(name);
        for (int i=0;i<20;i++){
            System.out.println(i);
        }
        return CommonResponse.success(result);
    }

    @ApiOperation(value = "数据导出")
    @GetMapping(value = "/api/release/exportUsers")
    public void exportUsers(HttpServletResponse response)throws Exception{
        List<User> userList = userMapper.selectAllUsers();
        if (CollectionUtils.isEmpty(userList)){
            return;
//            return CommonResponse.fail(2001,"无数据可导出");
        }else {
            String template = "users.xlsx";
            InputStream inputStream = TestController.class.getResourceAsStream("/templates/"+template);
            String fileName = "用户信息列表";
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition","attachment:filename="+ URLEncoder.encode(fileName+".xlsx","UTF-8"));
            SheetData sheetData = new SheetData(fileName);
            sheetData.addDatas(userList);
            new ExcelUtils().writeDate(inputStream,template,response.getOutputStream(),sheetData);
//            return CommonResponse.success();
        }

    }

}
