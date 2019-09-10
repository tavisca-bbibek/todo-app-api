package com.tavisca.workshop.todo.todorest.controller;

import com.tavisca.workshop.todo.todorest.exception.RepeatedRequestException;
import com.tavisca.workshop.todo.todorest.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RepeatedRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseMessage handleRepeatedRequestException(RepeatedRequestException e) {
        return new ResponseMessage("failure", e.getMessage());
    }
}
