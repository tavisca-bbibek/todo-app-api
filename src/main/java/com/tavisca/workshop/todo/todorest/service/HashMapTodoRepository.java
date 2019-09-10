package com.tavisca.workshop.todo.todorest.service;

import com.tavisca.workshop.todo.todorest.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HashMapTodoRepository implements TodoRepository {
    private Map<Integer, TodoItem> idToTodoItemMap = new HashMap<>();

    {
        idToTodoItemMap.put(1, new TodoItem(1, "Dance for no reason", "Because Jindagi jhand baa..."));
        idToTodoItemMap.put(2, new TodoItem(2, "Kill yourself", "Daemon the Thread"));
    }

    @Override

    public <S extends TodoItem> S save(S item) {
        idToTodoItemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public <S extends TodoItem> Iterable<S> saveAll(Iterable<S> items) {
        List<S> itemList = toList(items);
        itemList.forEach(item -> save(item));
        return itemList;
    }

    @Override
    public Optional<TodoItem> findById(Integer id) {
        return Optional.ofNullable(idToTodoItemMap.get(id));
    }

    @Override
    public boolean existsById(Integer id) {
        return idToTodoItemMap.containsKey(id);
    }

    @Override
    public Iterable<TodoItem> findAll() {
        return idToTodoItemMap.values();
    }

    @Override
    public Iterable<TodoItem> findAllById(Iterable<Integer> ids) {
        List<?> idList = toList(ids);
        return idList.stream().map(id -> idToTodoItemMap.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return idToTodoItemMap.size();
    }

    @Override
    public void deleteById(Integer id) {
        if (idToTodoItemMap.containsKey(id))
            idToTodoItemMap.remove(id);
    }

    @Override
    public void delete(TodoItem item) {
        idToTodoItemMap.remove(item.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends TodoItem> items) {
        items.forEach(item -> idToTodoItemMap.remove(item.getId()));
    }

    @Override
    public void deleteAll() {
        idToTodoItemMap.clear();
    }

    @Override
    public int generateNewId() {
        int id = (int) idToTodoItemMap.size();
        while(idToTodoItemMap.keySet().contains(id))
            id++;
        return id;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
