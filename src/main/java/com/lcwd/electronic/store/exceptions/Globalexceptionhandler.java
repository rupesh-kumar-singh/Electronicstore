package com.lcwd.electronic.store.exceptions;

import com.lcwd.electronic.store.dtos.ApiresponceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class Globalexceptionhandler {
    // reouce not found exception
    private Logger logger = LoggerFactory.getLogger(Globalexceptionhandler.class);

    @ExceptionHandler(Resourcenotfoundexception.class)
    public ResponseEntity<ApiresponceMessage> resourceNotfound(Resourcenotfoundexception ex) {
        logger.info(("exception handle invoked"));
        ApiresponceMessage build = ApiresponceMessage.builder().massage(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }


    // methodargumentnotvalidexception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlemethonargumentnotvalidexception(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> responce = new HashMap<>();
        allErrors.stream().forEach((objecterror) -> {
            String defaultMessage = objecterror.getDefaultMessage();
            String field = ((FieldError) objecterror).getField();
            responce.put(field, defaultMessage);
        });
        return new ResponseEntity<>(responce,HttpStatus.BAD_REQUEST);
    }



    // handle api
    @ExceptionHandler(BadapiRequest.class)
    public ResponseEntity<ApiresponceMessage> handlebadapirequest(BadapiRequest ex) {
        logger.info(("Bad api request"));
        ApiresponceMessage build = ApiresponceMessage.builder().massage(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);
    }
}