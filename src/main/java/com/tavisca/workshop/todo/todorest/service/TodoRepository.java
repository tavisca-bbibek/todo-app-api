package com.tavisca.workshop.todo.todorest.service;

import com.tavisca.workshop.todo.todorest.model.TodoItem;

import java.util.Optional;

public interface TodoRepository{

    <S extends TodoItem> S save(S entity);

    <S extends TodoItem> Iterable<S> saveAll(Iterable<S> entities);

    Optional<TodoItem> findById(Integer integer);

    boolean existsById(Integer integer);

    Iterable<TodoItem> findAll();

    Iterable<TodoItem> findAllById(Iterable<Integer> ids);

    long count();

    void deleteById(Integer integer);

    void delete(TodoItem entity);

    void deleteAll(Iterable<? extends TodoItem> entities);

    void deleteAll();

    int generateNewId();
}
