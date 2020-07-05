package com.zxyroy.fx.controller;

import com.zxyroy.fx.Constant;
import com.zxyroy.fx.exception.RateNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> springHandleNotFound(ConstraintViolationException apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", apiException.getMessage());
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }

    @ExceptionHandler(RateNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> springHandleNotFound(RateNotMatchException apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", "Rate is not match the amount, difference is :" + apiException.getDifference().doubleValue());
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> springHandleNotFound(ServerWebInputException apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        if (apiException.getRootCause() instanceof DateTimeParseException)
            errorResponse.put("message", "Time format incorrect, should be " + Constant.DATETIME_FORMAT);
        else {
            errorResponse.put("message", "Invalid Input");
        }
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HashMap<String, Object> springHandleNotFound(Exception apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", "Internal Error");
        log.error(apiException.getMessage(), apiException);
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }
}
