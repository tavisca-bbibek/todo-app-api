package com.tavisca.workshop.todo.todorest.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
        super("item not found");
    }
}
