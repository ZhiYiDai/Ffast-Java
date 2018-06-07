package cn.ffast.core.handler;

import cn.ffast.core.utils.ResultCode;
import cn.ffast.core.vo.ServiceResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        ServiceResult result = new ServiceResult(ResultCode.SERVICE_ERROR.getCode(),ResultCode.SERVICE_ERROR.getMessage());
        if(e!=null){
            result.addData("error", e.getMessage());
        }
        return JSONObject.toJSON(result);
    }
}