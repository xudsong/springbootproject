package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.dto.TokenInfoDto;
import com.xudasong.service.springcloudservice.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基类，提取公共逻辑
 */
@RestController
public abstract class BaseController {

    @Autowired
    private ITokenService tokenService;

    /**
     * 获取后台管理员信息
     * @return
     */
    protected TokenInfoDto getTokenInfo(){
        TokenInfoDto tokenInfo = tokenService.getTokenInfo();
        return tokenInfo;
    }

    /**
     * 获取C端用户信息
     * @return
     */
    protected String getUserId(){
        String userId = tokenService.getUserId();
        return userId;
    }

}
