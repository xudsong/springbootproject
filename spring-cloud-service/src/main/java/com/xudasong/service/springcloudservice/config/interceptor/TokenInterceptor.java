package com.xudasong.service.springcloudservice.config.interceptor;

import com.xudasong.service.springcloudservice.config.exception.BizException;
import com.xudasong.service.springcloudservice.service.IRedisService;
import com.xudasong.service.springcloudservice.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * 拦截获取登录信息
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor{

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IRedisService redisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String sessionId = request.getSession().getId();
        log.info("[{}] 当前请求 {}", sessionId, request.getRequestURI());

        //解析并获取到处理器
        if (handler instanceof HandlerMethod){
            //优先从请求头读取token值，没有的话从json或者param中读取
            String userInfo = request.getHeader("Authority-User");
            // URL转码
            if(StringUtils.isNotEmpty(userInfo)){
                userInfo = URLDecoder.decode(userInfo,"utf-8");
            }

            if (StringUtils.isEmpty(userInfo)){
                /**
                 * 不存在，则从token获取
                 */
                String token = request.getHeader("token");
                log.info("token:{} ",token);
                if (StringUtils.isBlank(token)){
                    throw new BizException(202,"您还没有登录");
                }
                userInfo = redisService.get(token);
            }
            log.info("[{}] 用户字符串 {}",sessionId, userInfo);
            //把后台管理系统用户信息读入当前会话中
            if (StringUtils.isNotEmpty(userInfo)){
                tokenService.readTokenInfo(userInfo);
            }
            return true;
        }
        return false;
    }

}
