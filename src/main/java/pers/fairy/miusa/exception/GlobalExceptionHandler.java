package pers.fairy.miusa.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.fairy.miusa.common.Result;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 15:06
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public Result handleGlobalException(GlobalException ge) {
        return Result.ERROR(ge.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException be) {
        ObjectError error = be.getAllErrors().get(0);
        return Result.LOGIN_ERROR(error.getDefaultMessage());
    }
}
