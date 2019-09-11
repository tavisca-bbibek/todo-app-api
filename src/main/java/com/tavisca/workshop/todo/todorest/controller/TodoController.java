package com.tavisca.workshop.todo.todorest.controller;

import com.tavisca.workshop.todo.todorest.exception.ItemNotFoundException;
import com.tavisca.workshop.todo.todorest.exception.RepeatedRequestException;
import com.tavisca.workshop.todo.todorest.model.ResponseMessage;
import com.tavisca.workshop.todo.todorest.model.ResponseMessageWithData;
import com.tavisca.workshop.todo.todorest.model.TodoItem;
import com.tavisca.workshop.todo.todorest.service.HashMapTodoRepository;
import com.tavisca.workshop.todo.todorest.service.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
public class TodoController {

    private static final String STATUS_SUCCESS = "success";

    @Autowired
    private TodoRepository todoService;

    TodoItem recentlySavedItem = null;

    @GetMapping(value = {"/todos", "/todo"})
    public ResponseMessageWithData<Iterable<TodoItem>> getAll() {
        return new ResponseMessageWithData<>(STATUS_SUCCESS, "list of all todo items", todoService.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseMessageWithData<TodoItem> getById(@PathVariable Integer id) throws ItemNotFoundException {
        Optional<TodoItem> mayBeItem = todoService.findById(id);
        if (mayBeItem.isPresent())
            return new ResponseMessageWithData<>(STATUS_SUCCESS, "Item retrieved", mayBeItem.get());
        else
            throw new ItemNotFoundException();
    }

    @PostMapping("/todo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessageWithData<TodoItem> createItem(@Valid @RequestBody TodoItem item) throws RepeatedRequestException {
        if (recentlySavedItem != null && recentlySavedItem.isSame(item))
            throw new RepeatedRequestException();

        TodoItem finalItem = new TodoItem(todoService.generateNewId(), item.getTitle(), item.getDescription());
        recentlySavedItem = item;
        return new ResponseMessageWithData<>(STATUS_SUCCESS, "item added", todoService.save(finalItem));
    }

    @PutMapping("/todo/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessageWithData<TodoItem> putItem(@PathVariable Integer id,
                                                     @Valid @RequestBody TodoItem item) throws ItemNotFoundException {

        Optional<TodoItem> maybeItem = todoService.findById(id);
        TodoItem finalItem = new TodoItem(id, item.getTitle(), item.getDescription());

        if (maybeItem.isPresent())
            return new ResponseMessageWithData<>(STATUS_SUCCESS, "item updated", todoService.save(finalItem));
        else
            throw new ItemNotFoundException();
    }

    @PatchMapping("/todo/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseMessageWithData<TodoItem> patchItem(@PathVariable Integer id,
                                                       @RequestBody TodoItem item) throws ItemNotFoundException {
        Optional<TodoItem> maybeItem = todoService.findById(id);

        if (maybeItem.isPresent()) {
            TodoItem finalItem = new TodoItem(id,
                    item.getTitle() == null ? maybeItem.get().getTitle() : item.getTitle(),
                    item.getDescription() == null ? maybeItem.get().getDescription() : item.getDescription());
            return new ResponseMessageWithData<>(STATUS_SUCCESS, "item updated", todoService.save(finalItem));
        } else
            throw new ItemNotFoundException();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseMessage deleteItem(@PathVariable Integer id) throws ItemNotFoundException {
        Optional<TodoItem> mayBeItem = todoService.findById(id);

        if (mayBeItem.isPresent()) {
            todoService.delete(mayBeItem.get());
            if (todoService.count() == 0)
                recentlySavedItem = null;
            return new ResponseMessage(STATUS_SUCCESS, "item deleted");
        } else
            throw new ItemNotFoundException();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseMessage handleItemNotFoundException(ItemNotFoundException e) {
        return new ResponseMessage("failure", e.getMessage());
    }
}
