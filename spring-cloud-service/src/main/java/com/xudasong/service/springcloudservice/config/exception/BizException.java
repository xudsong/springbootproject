package com.xudasong.service.springcloudservice.config.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * @author song
 * @deprecated 业务系统异常处理类
 */
@Data
public class BizException extends RuntimeException implements Serializable{

    /** 错误代码 */
    private Integer exceptionCode;

    /** 错误描述 */
    private String exceptionInfo;

    /**
     *
     * @param exceptionCode
     */
    public BizException(Integer exceptionCode){
        super(exceptionCode.toString());
        this.setExceptionCode(exceptionCode);
    }

    /**
     *
     * @param exceptionCode
     * @param exceptionInfo
     */
    public BizException(int exceptionCode,String exceptionInfo){
        super("错误代码：[" + exceptionCode + "]，错误描述：" + exceptionInfo);
        this.setExceptionCode(exceptionCode);
        this.setExceptionInfo(exceptionInfo);
    }

    /**
     *
     * @param exceptionCode
     * @param throwable 传递的异常
     */
    public BizException(Integer exceptionCode,Throwable throwable){
        super(exceptionCode.toString(),throwable);
        this.setExceptionCode(exceptionCode);
    }

    /**
     *
     * @param exceptionCode
     * @param exceptionInfo
     * @param throwable
     */
    public BizException(int exceptionCode,String exceptionInfo,Throwable throwable){
        super("错误代码：[" + exceptionCode + "]，错误描述：" + exceptionInfo,throwable);
        this.setExceptionCode(exceptionCode);
        this.setExceptionInfo(exceptionInfo);
    }

    @Override
    public String toString(){
        return this.getMessage();
    }

}
