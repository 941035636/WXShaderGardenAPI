package com.jt.www.aop;


import com.jt.www.exception.BizException;
import com.jt.www.exception.ParamException;
import com.jt.www.exception.ServiceException;
import com.jt.www.model.enums.ResultEnum;
import com.jt.www.model.reps.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@Slf4j
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity defultExcepitonHandler(Exception e) {
        e.printStackTrace();
        StringBuilder result = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            for (FieldError fieldErro : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
                result.append(fieldErro.getDefaultMessage());
                break;
            }
            return GenericResponse.ng(result.toString());
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            for (ConstraintViolation constraintViolation : constraintViolationException.getConstraintViolations()) {
                result.append(constraintViolation.getMessage());
                break;
            }
            return GenericResponse.ng(result.toString());
        } else if (e instanceof BizException || e instanceof ParamException) {
            return GenericResponse.ng(e.getMessage());
        } else if (e instanceof ServiceException) {
            e.printStackTrace();
            return GenericResponse.ng(ResultEnum.SERVER_ERROR.getMsg());
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            e.printStackTrace();
            return GenericResponse.ng(ResultEnum.PARAM_ERROR.getMsg());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            e.printStackTrace();
            return GenericResponse.ng(ResultEnum.METHOD_NOT_ALLOWED.getMsg());
        } else if (e instanceof BadSqlGrammarException) {
            e.printStackTrace();
            return GenericResponse.ng(ResultEnum.SERVER_ERROR.getMsg());
        } else {
            e.printStackTrace();
            return GenericResponse.ng(e.getMessage());
        }
    }

}
