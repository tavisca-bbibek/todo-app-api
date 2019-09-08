package com.tavisca.workshop.todo.todorest.model;

import javax.validation.constraints.NotBlank;

public class TodoItem {
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public TodoItem() { }

    public TodoItem(int id, @NotBlank String title, @NotBlank String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSame(TodoItem item){
        return this.getTitle().equals(item.getTitle()) && this.getDescription().equals(item.getDescription());
    }
}
