package com.tavisca.workshop.todo.todorest.controller;

import com.tavisca.workshop.todo.todorest.exception.RepeatedRequestException;
import com.tavisca.workshop.todo.todorest.model.FieldError;
import com.tavisca.workshop.todo.todorest.model.ResponseMessage;
import com.tavisca.workshop.todo.todorest.model.ResponseMessageWithData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RepeatedRequestException.class)
    ResponseMessage handleRepeatedRequestException(RepeatedRequestException e) {
        return new ResponseMessage("failure", e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseMessageWithData<List<FieldError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseMessageWithData<>("failure", "invalid request format", errors);
    }
}
