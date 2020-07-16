package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

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
