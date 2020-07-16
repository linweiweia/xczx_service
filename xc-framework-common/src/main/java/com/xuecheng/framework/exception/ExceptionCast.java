package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author linweiwei
 * @date 2020-07-16 15:29
 * @describe: 抛出异常，就是封装一下异常类
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
