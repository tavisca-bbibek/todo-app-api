package com.tavisca.workshop.todo.todorest.controller;

import com.tavisca.workshop.todo.todorest.model.ResponseMessage;
import com.tavisca.workshop.todo.todorest.model.TodoItem;
import com.tavisca.workshop.todo.todorest.service.HashMapTodoRepository;
import com.tavisca.workshop.todo.todorest.service.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TodoController {

    private static final String STATUS_FAILURE = "failure";
    private static final String STATUS_SUCCESS = "success";
    private TodoRepository todoService = new HashMapTodoRepository();

    @GetMapping(value = {"/todos", "/todo"})
    public ResponseMessage<Iterable<TodoItem>> getAll() {
        return new ResponseMessage<>(STATUS_SUCCESS, "list of all todo items", todoService.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ResponseMessage> getById(@PathVariable Integer id) {
        Optional<TodoItem> mayBeItem = todoService.findById(id);
        return mayBeItem.map(todoItem -> new ResponseEntity<>(
                new ResponseMessage(STATUS_SUCCESS, "Item retrieved", todoItem),
                HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        new ResponseMessage(STATUS_FAILURE, "Item with id " + id + " doesn't exist", null),
                        HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/todo")
    public ResponseEntity<ResponseMessage> createItem(@Valid @RequestBody TodoItem item) {
        int todoCount = (int) todoService.count();
        if(todoService.findById(todoCount).get().isSame(item))
           return new ResponseEntity<>(
                    new ResponseMessage(STATUS_FAILURE, "Repeated request.", null),
                    HttpStatus.CONFLICT);

        TodoItem finalItem = new TodoItem(todoCount + 1, item.getTitle(), item.getDescription());
        Optional<TodoItem> mayBeItem = todoService.findById(item.getId());
        return mayBeItem.map(
                todoItem -> new ResponseEntity<>(
                        new ResponseMessage(STATUS_FAILURE, "Item already exists", null),
                        HttpStatus.BAD_REQUEST))
                .orElseGet(() ->
                        new ResponseEntity<>(
                                new ResponseMessage<>(STATUS_SUCCESS, "Item created", todoService.save(finalItem)),
                                HttpStatus.CREATED));
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity putItem(@PathVariable Integer id,
                                  @Valid @RequestBody TodoItem item) {

        Optional<TodoItem> maybeItem = todoService.findById(id);
        TodoItem finalItem = new TodoItem(id, item.getTitle(), item.getDescription());

        return maybeItem.map(todoItem -> new ResponseEntity<>(
                new ResponseMessage(STATUS_SUCCESS, "item updated", todoService.save(finalItem)),
                HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity(
                        new ResponseMessage<>(STATUS_FAILURE, "item doesn't exist ", null),
                        HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ResponseMessage> deleteItem(@PathVariable Integer id) {
        Optional<TodoItem> mayBeItem = todoService.findById(id);

        return mayBeItem.map(todoItem -> {
            todoService.delete(todoItem);
            return new ResponseEntity(
                    new ResponseMessage(STATUS_SUCCESS, "item deleted", null),
                    HttpStatus.OK);
        })
                .orElseGet(() ->
                        new ResponseEntity<>(
                                new ResponseMessage(STATUS_FAILURE, "item doesn't exist", null),
                                HttpStatus.BAD_REQUEST));
    }
}
