package com.xudasong.service.springcloudservice.enums;

public enum ErrorCode {

    /** 所有未知错误 */
    UNKNOWN_ERROR(200000,"系统错误，请联系管理人员"),

    /**
     * 控制层请求参数验证失败时抛出的错误
     */
     VALID_ERROR(200001,"请求参数验证失败"),

    /**
     * 控制层请求找不到对应的路径
     */
    HANDLER_NOTFOUND_ERROR(200006,"找不到对应的路径"),

    /**
     * 请求被拒绝，一般为请求方法不对等
     */
    REQUEST_DECLNED(200007,"你当前的请求被拒绝"),

    /**
     * 缺少请求的参数
     */
    LESS_PARAM_ERROR(200008,"缺少请求的参数");

    private final int code;
    private final String message;

    private ErrorCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
