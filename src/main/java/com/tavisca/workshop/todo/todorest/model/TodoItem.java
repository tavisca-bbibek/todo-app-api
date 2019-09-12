package com.tavisca.workshop.todo.todorest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public static final int ID_NONE = 0;

    @NotBlank
    private String title;
    private String description;

    public TodoItem() {
    }

    public TodoItem(int id, @NotBlank String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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
