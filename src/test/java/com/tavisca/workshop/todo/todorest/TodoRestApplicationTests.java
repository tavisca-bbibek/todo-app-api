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
}
