package com.ra.advice;



import com.ra.exception.DataNotFoundException;
import com.ra.exception.NoDataException;
import com.ra.model.dto.response.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//
@Slf4j
@RestControllerAdvice
public class APIControllerAdvice {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,Object> forbidden(AccessDeniedException e){
        Map<String,Object> map = new HashMap<>();
        map.put("error",new ResponseError(403,"FOR_BIDDEN",e));
        return  map;
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> notFound(DataNotFoundException e){
        log.info("not found",e.getMessage());
        Map<String,String> map = new HashMap<>();
        map.put("data",null);
        return map;
    }

    @ExceptionHandler(NoDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,ArrayList> noData(NoDataException e){
        log.info("no data",e.getMessage());
        Map<String,ArrayList> map = new HashMap<>();
        map.put("data",new ArrayList<>());
        return map;
    }
}