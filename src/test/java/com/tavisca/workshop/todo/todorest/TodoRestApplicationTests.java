package com.tavisca.workshop.todo.todorest;

import com.tavisca.workshop.todo.todorest.model.ResponseMessageWithData;
import com.tavisca.workshop.todo.todorest.model.TodoItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRestApplicationTests {

	@Test
	public void contextLoads() {
	}

	/*
	//Test using RestTemplate
	@Test
	public void canCreateReadAndDeleteTodoItem(){
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/todo";

		TodoItem item = new TodoItem(1, "test", "test description");
		ResponseMessageWithData<TodoItem> response = restTemplate.postForObject(url, item, ResponseMessageWithData.class);
		ResponseMessageWithData<TodoItem> expected = new ResponseMessageWithData<>("success", "item added", item);
		Assertions.assertThat(response).isSameAs(expected);
	}*/

	@Test
	public void canCreateReadAndDeleteTodoItem(){
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/todo";

		TodoItem item = new TodoItem(1, "test", "test description");
		ResponseMessageWithData<TodoItem> response = restTemplate.postForObject(url, item, ResponseMessageWithData.class);
		ResponseMessageWithData<TodoItem> expected = new ResponseMessageWithData<>("success", "item added", item);
		Assertions.assertThat(response).isSameAs(expected);
	}

	

}
