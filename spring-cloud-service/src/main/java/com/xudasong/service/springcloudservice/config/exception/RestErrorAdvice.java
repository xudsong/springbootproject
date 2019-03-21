package com.xudasong.service.springcloudservice.config.exception;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.xudasong.service.springcloudservice.enums.ErrorCode.*;

/**
 * 异常拦截
 * @author song
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class RestErrorAdvice {

    /**
     * 总的错误界面
     * @param e 错误
     * @return 提示用户错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    private CommonResponse<Void> handleException(Exception e){
        log.error("未知错误",e);
        return CommonResponse.fail(UNKNOWN_ERROR.getCode(),UNKNOWN_ERROR.getMessage());
    }

    /**
     * 请求方法不对拦截返回
     * @param e 请求方法不对错误
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private CommonResponse<Void> handleException(HttpRequestMethodNotSupportedException e){
        log.warn("请求方法不对");
        return CommonResponse.fail(REQUEST_DECLNED.getCode(),REQUEST_DECLNED.getMessage());
    }

    /**
     * 请求体拦截返回
     * @param e 请求体Content-type不对错误
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    private CommonResponse<Void> handleException(HttpMediaTypeNotSupportedException e){
        log.warn("请求体Content-type不对");
        return CommonResponse.fail(REQUEST_DECLNED.getCode(),REQUEST_DECLNED.getMessage());
    }

    /**
     * 没有跳转地址，404找不到的异常处理
     * @param e
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoHandlerFoundException.class)
    private CommonResponse<Void> handleException(NoHandlerFoundException e){
        log.warn("找不到地址对应的处理器");
        return CommonResponse.fail(HANDLER_NOTFOUND_ERROR.getCode(),HANDLER_NOTFOUND_ERROR.getMessage());
    }

    /**
     * 业务错误界面
     * @param e
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    private CommonResponse<Void> handleException(BizException e){
        log.warn("业务错误");
        return CommonResponse.fail(e.getExceptionCode(),e.getExceptionInfo());
    }

    /**
     * Valid注解中 - requestParam缺少参数
     * @param e
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private CommonResponse<Void> handleException(MissingServletRequestParameterException e){
        String message = String.format("%s 字段缺失",e.getParameterName());
        log.warn("字段缺失出错：" + message,e);
        return CommonResponse.fail(VALID_ERROR.getCode(),VALID_ERROR.getMessage());
    }

    /**
     * Valid注解中 - 校验中参数不符合规则错误（请求体为urlParameter）
     * @param e
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private CommonResponse<Void> handleException(MethodArgumentNotValidException e){

        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String message = String.format("%s",error.getDefaultMessage());

        String parameters = Arrays.asList(error.getArguments()).stream().map(String::valueOf).collect(Collectors.joining(","));
        String logMsg = String.format("%s [%s]%s - %s  [%s]",error.getDefaultMessage(),
                error.getField(),error.getObjectName(),error.getRejectedValue(),parameters);
        log.warn("字段不符合规则出错：" + logMsg,e);
        return CommonResponse.fail(VALID_ERROR.getCode(),message);
    }

    /**
     * Valid注解中 - 校验中参数不符合规则错误（请求体为Json）
     * @param e
     * @return 提示用户请求失败
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    private CommonResponse<List<String>> handleException(BindException e){

        List<String> fieldErrors = e.getFieldErrors().stream().map(fieldError -> String.format("%s",fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        log.warn("字段不符合规则出错：" + fieldErrors.stream().collect(Collectors.joining(",")),e);
        return CommonResponse.fail(VALID_ERROR.getCode(),VALID_ERROR.getMessage(),fieldErrors);
    }

    /**
     * Valid注解中 - 校验中缺少请求包体（缺少或者content-type对不上）
     * @param e
     * @return 提示无法转换消息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private CommonResponse<Void> handleException(HttpMessageNotReadableException e){
        log.warn("缺少请求参数出错：" + e.getMessage());
        return CommonResponse.fail(LESS_PARAM_ERROR.getCode(),LESS_PARAM_ERROR.getMessage());
    }

    /**
     * Valid注解中 - 校验中缺参数无法转换错误（请求参数类型对不上，数值传了字符串）
     * @param e
     * @return 提示无法转换消息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private CommonResponse<Void> handleException(MethodArgumentTypeMismatchException e){
        String field = e.getName();
        String message = String.format("%s: 无法转换 %s",field,e.getValue());
        log.warn("字段无法转换出错：" + message,e);
        return CommonResponse.fail(VALID_ERROR.getCode(),message);
    }

    /**
     * Valid注解中 - 校验中参数不符合规则出错（请求参数校验规则不通过）
     * @param e
     * @return 提示无法转换消息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    private CommonResponse<Void> handleException(ConstraintViolationException e){
        String field;
        StringBuilder sBuilder = new StringBuilder();
        StringBuilder logBuilder = new StringBuilder();
        for(ConstraintViolation<?> constraint : e.getConstraintViolations()){
            field = constraint.getPropertyPath().toString();
            if(sBuilder.length()>0){
                sBuilder.append(",");
            }
            logBuilder.append(String.format("%s:%s",field,constraint.getMessage()));
            sBuilder.append(constraint.getMessage());
        }
        log.warn("字段符合规则出错：" + logBuilder.toString(),e);
        return CommonResponse.fail(VALID_ERROR.getCode(),sBuilder.toString());
    }

}
