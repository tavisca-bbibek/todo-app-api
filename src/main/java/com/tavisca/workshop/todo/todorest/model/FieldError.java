package com.tavisca.workshop.todo.todorest.model;

public class FieldError {
    private String name;
    private  String message;

    public FieldError(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
