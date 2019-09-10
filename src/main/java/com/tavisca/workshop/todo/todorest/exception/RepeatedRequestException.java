package com.tavisca.workshop.todo.todorest.exception;

public class RepeatedRequestException extends Exception {
    public RepeatedRequestException() {
        super("repeated request");
    }
}
