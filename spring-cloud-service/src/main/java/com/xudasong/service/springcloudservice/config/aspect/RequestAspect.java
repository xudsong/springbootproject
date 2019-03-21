package com.xudasong.service.springcloudservice.config.aspect;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拦截记录请求
 * @author song
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

    @Pointcut("execution(public * com.xudasong.service.springcloudservice.controller..*.*(..))")
    public void log(){}

    @Before("log()")
    public void exBefore(JoinPoint joinPoint){
        List<Object> logArgs = Arrays.asList(joinPoint.getArgs()).stream()
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        log.info("接收到请求到 -> {} .{} - 参数: {} ", joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(), JSON.toJSONString(logArgs));
    }

    @After("log()")
    public void exAfter(JoinPoint joinPoint){

        log.info("{} . {} 处理完毕 ：",joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void exAfterReturning(Object result){
        log.info("返回：{}",JSON.toJSONString(result));
    }

}
