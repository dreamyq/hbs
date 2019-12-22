package cn.codemodel.model.exception;

import cn.codemodel.model.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BaseExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){
        e.printStackTrace();
        if (e.getClass()==CommonException.class){
            CommonException ce = (CommonException) e;
            return new Result(ce.getCode());
        }else {
            return Result.ERROR();
        }
    }
}
