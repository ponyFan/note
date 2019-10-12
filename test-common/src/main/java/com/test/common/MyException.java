package com.test.common;

import org.springframework.web.bind.annotation.ExceptionHandler;

/*@ControllerAdvice*/
public class MyException extends Exception{

    @ExceptionHandler(Exception.class)
    public String handle(Exception e){
        return e.getCause().getCause().getMessage();
    }
}
