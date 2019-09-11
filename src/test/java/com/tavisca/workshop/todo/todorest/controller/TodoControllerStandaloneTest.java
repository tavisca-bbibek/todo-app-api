package com.tavisca.workshop.todo.todorest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tavisca.workshop.todo.todorest.model.TodoItem;
import com.tavisca.workshop.todo.todorest.service.TodoRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerStandaloneTest {

    @MockBean
    TodoRepository todoRepository;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void respondsWithListOfTodoItemOnGetRequest() throws Exception {
        TodoItem item = new TodoItem();
        item.setTitle("title");
        List<TodoItem> items = Arrays.asList(item);

        Mockito.when(todoRepository.findAll())
                .thenReturn(items);

        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data[0].title", Matchers.is("title")));
    }

    @Test
    public void respondsWithItemWithIdOnGetWith_Id_Parameter() throws Exception {
        TodoItem item = new TodoItem();
        item.setTitle("title");

        Mockito.when(todoRepository.findById(1))
                .thenReturn(Optional.of(item));

        mockMvc.perform(get("/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", Matchers.is("title")));
    }

    @Test
    public void addsItemWhenRequestedPostWithAnItem() throws Exception {
        TodoItem item = new TodoItem();
        item.setTitle("title");
        item.setDescription(("description"));
        List<TodoItem> items = Arrays.asList(item);

        Mockito.when(todoRepository.findAll()).thenReturn(items);

        mockMvc.perform(post("/todo")
                .content(mapper.writeValueAsString(item))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title", Matchers.is("title")));
    }

    @Test
    public void modifiesItemWhenRequestedPutWithAnItem() throws Exception {
        TodoItem oldItem = new TodoItem(0, "title", "description");
        TodoItem newItem = new TodoItem(0, "new-title", "new-description");

        Mockito.when(todoRepository.findById(0)).thenReturn(Optional.of(oldItem));

        mockMvc.perform(
                put("/todo/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newItem))
        ).andExpect(status().isAccepted());

        Mockito.when(todoRepository.findById(0)).thenReturn(Optional.of(newItem));
        mockMvc.perform(get("/todo/0"))
                .andExpect(jsonPath("$.data.title", Matchers.is("new-title")))
                .andExpect(jsonPath("$.data.description", Matchers.is("new-description")));
    }

    @Test
    public void updatesGivenItemFieldsOnPatchWithAPartialItem() throws Exception {
        TodoItem oldItem = new TodoItem(0, "title", "description");
        TodoItem newItem = new TodoItem(0, "new-title", "description");

        Mockito.when(todoRepository.findById(0)).thenReturn(Optional.of(oldItem));

        mockMvc.perform(
                patch("/todo/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newItem))
        ).andExpect(status().isAccepted());

        Mockito.when(todoRepository.findById(0)).thenReturn(Optional.of(newItem));
        mockMvc.perform(get("/todo/0"))
                .andExpect(jsonPath("$.data.title", Matchers.is("new-title")))
                .andExpect(jsonPath("$.data.description", Matchers.is("description")));
    }

    @Test
    public void deletesItemWhenRequestedDeleteWith_Id_Parameter() throws Exception {
        TodoItem item = new TodoItem(0, "title", "description");
        List<TodoItem> items = Arrays.asList(item);

        Mockito.when(todoRepository.findById(0)).thenReturn(Optional.of(item));
        Mockito.when(todoRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(delete("/todo/0")
                .content(mapper.writeValueAsString(item))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("success")));

        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", Matchers.hasSize(0)));
    }
}
