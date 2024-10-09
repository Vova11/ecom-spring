package com.many.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err-> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException ex){
        APIResponse apiResponse = new APIResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException ex){
//        String message =  ex.getMessage();
//        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> myApiException(ApiException ex){
        String message =  ex.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
