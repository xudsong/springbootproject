package com.xudasong.service.springcloudservice.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xudasong.service.springcloudservice.config.exception.BizException;
import com.xudasong.service.springcloudservice.dto.TokenInfoDto;
import com.xudasong.service.springcloudservice.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 处理令牌逻辑
 */
@Service
@Slf4j
public class TokenServiceImpl implements ITokenService{

    /**
     * 存放在后台管理系统的token
     */
    private static ThreadLocal<TokenInfoDto> threadLocal = new ThreadLocal<>();

    /**
     * 存放C端token
     */
    private static ThreadLocal<String> cThreadLocal = new ThreadLocal<>();

    /**
     * 网关加密后的用户信息解密
     * @param userInfoString 解码后的用户信息json字符串
     */
    @Override
    public void readTokenInfo(String userInfoString) {
        if (null == userInfoString || userInfoString.isEmpty()){
            throw new BizException(203,"无法解析用户信息");
        }
        //修改token 即为用户字符串，转为utf-8形式
        JSONObject jsonObject = JSON.parseObject(userInfoString);
        if (Objects.isNull(jsonObject.get("tenantId"))){
            String userId = (String) jsonObject.get("userId");
            cThreadLocal.set(userId);
            log.info("检测到C端用户[{}]登录",userId);
        }else {
            TokenInfoDto tokenInfo = JSONObject.parseObject(userInfoString, TokenInfoDto.class);
            if(null == tokenInfo){
                throw new BizException(203,"无法解析用户信息");
            }
            threadLocal.set(tokenInfo);
            log.info("检测到后台管理用户[{}]登录",tokenInfo.getName());
        }
    }

    @Override
    public TokenInfoDto getTokenInfo() {
        TokenInfoDto tokenInfoDto = threadLocal.get();
        if(null == tokenInfoDto){
            log.warn("无法获取登录信息");
            throw new BizException(203,"请先登录");
        }
        return tokenInfoDto;
    }

    @Override
    public String getUserId() {
        String userId = cThreadLocal.get();
        if(null == userId){
            log.warn("无法获取登录信息");
            throw new BizException(203,"请先登录");
        }
        return userId;
    }


}
