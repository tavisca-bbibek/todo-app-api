package com.tavisca.workshop.todo.todorest.model;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class TodoItem {
    private int id;

    @NotBlank
    private String title;

    private String description;

    public TodoItem() { }

    public TodoItem(int id, @NotBlank String title, String description) {
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

    public boolean isSame(TodoItem todoItem) {
        if (this == todoItem) return true;
        if (todoItem == null || getClass() != todoItem.getClass()) return false;
        return Objects.equals(title, todoItem.title) &&
                Objects.equals(description, todoItem.description);
    }
}
