package com.tavisca.workshop.todo.todorest.service;

import com.tavisca.workshop.todo.todorest.model.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<TodoItem, Integer> {
    
}
