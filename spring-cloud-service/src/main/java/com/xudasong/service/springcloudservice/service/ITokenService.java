package com.xudasong.service.springcloudservice.service;

import com.xudasong.service.springcloudservice.dto.TokenInfoDto;

/**
 * @author song
 */
public interface ITokenService {

    /**
     * 根据当前请求获取当前登录信息
     * @param userInfoString 解码后的用户信息json字符串
     */
    void readTokenInfo(String userInfoString);

    /**
     * 根据当前请求获取当前登录信息
     * @return 后台管理系统的登录令牌信息
     */
    TokenInfoDto getTokenInfo();

    /**
     * 根据当前请求获取当前登录信息
     * @return C端用户登录令牌信息userId
     */
    String getUserId();
}
