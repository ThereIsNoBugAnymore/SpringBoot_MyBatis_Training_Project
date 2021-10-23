package com.example.admin;

import com.example.admin.exceptions.ParamsException;
import com.example.admin.model.RespBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParamsException.class)
    @ResponseBody
    public RespBean paramsExceptionHandler(ParamsException e) {
        return RespBean.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RespBean exceptionHandler(Exception e) {
        return RespBean.error(e.getMessage());
    }
}
