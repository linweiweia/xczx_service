package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author linweiwei
 * @version 1.0
 * @date 2020-07-16 15:38
 * @describe: SpringMVC异常增强器（异常统一捕获器）
 */
@ControllerAdvice//控制器增强器
public class ExceptionCatch {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //定义map
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONMAPS;
    //定义构造map的build
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();
    //添加第三方异常的常出现异常，出现异常先找，有则匹配返回,可以多匹配一些。
    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    //异常处理器，捕获第三方抛出的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        if (EXCEPTIONMAPS == null){
            EXCEPTIONMAPS = builder.build();
        }
        //第三方异常的常出现异常，出现异常先找，有则匹配返回,如果找不到给用户响应99999异常。
        ResultCode resultCode = EXCEPTIONMAPS.get(exception.getClass());
        if (resultCode != null){
            return new ResponseResult(resultCode);
        }else{
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    //异常处理器，捕获自定义异常类
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        //记录错误
        LOGGER.error("catch exception:{}", customException.getMessage());
        //返回结果（感觉这里有点问题，不知道在并发的情况下这个getResultCode会不会有问题，可能框架做了控制）
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);//设计有点问题ResponseResult和ResultCode内容一样
    }



}
