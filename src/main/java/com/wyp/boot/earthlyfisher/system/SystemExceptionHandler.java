package com.wyp.boot.earthlyfisher.system;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 此处主要是@ControllerAdvice在起作用,当然如果继承<span>ResponseEntityExceptionHandler</span>
 * <br/>
 * 可以处理<span>ResponseEntityExceptionHandler</span>已经实现的异常处理,<br/>
 * 并且可以统一调用`handleExceptionInternal`方法实现返回<span>ResponseEntity<Object></span>
 *
 * @author earthlyfisher
 */
@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleControllerException(Exception exception, HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity(status.value() + "", exception.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
