package com.example.boot.springboottemplatestarter.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;

import com.example.boot.springboottemplatestarter.common.ResponseBodyBean;
import com.example.boot.springboottemplatestarter.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * Description: ExceptionControllerAdvice
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com
 * @date 2019-03-02 17:39
 **/
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {
    /**
     * <p>Exception handler.</p>
     * <p><strong>ATTENTION</strong>: In this method, <strong><em>cannot throw any exception</em></strong>.</p>
     *
     * @param request   HTTP request
     * @param exception any kinds of exception occurred in controller
     * @return custom exception info
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseBodyBean handleException(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Exception exception) {

        if (exception instanceof NoHandlerFoundException) {
            log.error("[GlobalExceptionCapture] NoHandlerFoundException: Request URL = {}, HTTP method = {}",
                    ((NoHandlerFoundException) exception).getRequestURL(),
                    ((NoHandlerFoundException) exception).getHttpMethod());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return ResponseBodyBean.ofStatus(HttpStatus.NOT_FOUND);
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            log.error("[GlobalExceptionCapture] HttpRequestMethodNotSupportedException: " +
                            "Current method is {}, Support HTTP method = {}",
                    ((HttpRequestMethodNotSupportedException) exception).getMethod(),
                    JSONUtil.toJsonStr(
                            ((HttpRequestMethodNotSupportedException) exception).getSupportedHttpMethods()));
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            return ResponseBodyBean.ofStatus(HttpStatus.METHOD_NOT_ALLOWED);
        } else if (exception instanceof MethodArgumentNotValidException) {
            log.error("[GlobalExceptionCapture] MethodArgumentNotValidException: {}", exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseBodyBean.ofStatus(HttpStatus.BAD_REQUEST.value(),
                    getFieldErrorMessageFromException((MethodArgumentNotValidException) exception),
                    null);
        } else if (exception instanceof ConstraintViolationException) {
            log.error("[GlobalExceptionCapture] ConstraintViolationException: {}", exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseBodyBean.ofStatus(HttpStatus.BAD_REQUEST.value(),
                    CollUtil.getFirst(((ConstraintViolationException) exception)
                            .getConstraintViolations())
                            .getMessage(), null);
        } else if (exception instanceof BaseException) {
            log.error("[GlobalExceptionCapture] BaseException: Status code: {}, message: {}, data: {}",
                    ((BaseException) exception).getCode(),
                    exception.getMessage(),
                    ((BaseException) exception).getData());
            response.setStatus(((BaseException) exception).getCode());
            return ResponseBodyBean.ofStatus(((BaseException) exception).getCode(),
                    exception.getMessage(),
                    ((BaseException) exception).getData());
        }

        log.error("[GlobalExceptionCapture]: Exception information: {} ", exception.getMessage(), exception);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseBodyBean.ofStatus(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    /**
     * Get field error message from exception. If two or more fields do not pass Spring Validation check, then will
     * return the 1st error message of the error field.
     *
     * @param exception MethodArgumentNotValidException
     * @return field error message
     */
    private String getFieldErrorMessageFromException(MethodArgumentNotValidException exception) {
        try {
            DefaultMessageSourceResolvable firstErrorField =
                    (DefaultMessageSourceResolvable) Objects.requireNonNull(exception.getBindingResult()
                            .getAllErrors()
                            .get(0)
                            .getArguments())[0];
            String firstErrorFieldName = firstErrorField.getDefaultMessage();
            String firstErrorFieldMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return firstErrorFieldName + " " + firstErrorFieldMessage;
        } catch (Exception e) {
            log.error("Exception occurred when get field error message from exception. Exception message: {}",
                    e.getMessage(),
                    e);
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
    }
}
