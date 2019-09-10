package com.tavisca.workshop.todo.todorest.model;

public class ResponseMessageWithData<T>  extends ResponseMessage{
    private T data;

    public ResponseMessageWithData(String status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
