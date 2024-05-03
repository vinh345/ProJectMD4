package com.ra.advice;



import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ra.exception.DataConflictException;
import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.response.ResponseError;

import lombok.extern.slf4j.Slf4j;
//
@Slf4j
@RestControllerAdvice
public class APIControllerAdvice {

    /**
     * 401 Unauthorized.
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,ResponseError> unauthorized(AuthenticationException e){
        log.info("UNAUTHORIZED",e.getMessage());
        Map<String,ResponseError> map = new HashMap<>();
        map.put("error",new ResponseError(401,"UNAUTHORIZED",e.getMessage()));
        return  map;
    }

    /**
     * 403,FORBIDDEN
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,ResponseError> forbidden(AccessDeniedException e){
        log.info("FORBIDDEN",e.getMessage());
        Map<String,ResponseError> map = new HashMap<>();
        map.put("error",new ResponseError(403,"FORBIDDEN",e.getMessage()));
        return  map;
    }

    @ExceptionHandler(IdFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,ResponseError> badIdFormat(IdFormatException e){
        log.info("ID_FORMAT",e.getMessage());
        Map<String,ResponseError> map = new HashMap<>();
        map.put("error",new ResponseError(400,"ID_FORMAT",e.getMessage()));
        return map;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,ResponseError> notFound(DataNotFoundException e){
        log.info("NOT_FOUND",e.getMessage());
        Map<String,ResponseError> map = new HashMap<>();
        map.put("error",new ResponseError(404,"NOT_FOUND",e.getMessage()));
        return map;
    }

    @ExceptionHandler(DataConflictException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,ResponseError> dataConflict(DataConflictException e){
        log.info("DATA_CONFLICT",e.getMessage());
        Map<String,ResponseError> map = new HashMap<>();
        map.put("error",new ResponseError(400,"DATA_CONFLICT",e.getMessage()));
        return map;
    }
}