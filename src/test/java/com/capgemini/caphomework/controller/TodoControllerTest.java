package com.capgemini.caphomework.controller;

import com.capgemini.caphomework.dto.TodoResponse;
import com.capgemini.caphomework.entity.TodoStatus;
import com.capgemini.caphomework.exception.TodoNotFoundException;
import com.capgemini.caphomework.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTodo() throws Exception {

        TodoResponse response = new TodoResponse(
                1L,
                "Learn Spring Boot",
                TodoStatus.OPEN,
                LocalDateTime.now()
        );

        when(todoService.createTodo(any()))
                .thenReturn(response);

        String requestBody = """
                {
                    "title": "Learn Spring Boot"
                }
                """;

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Learn Spring Boot"))
                .andExpect(jsonPath("$.status")
                        .value("OPEN"));
    }

    @Test
    void shouldReturnAllTodos() throws Exception {

        TodoResponse response = new TodoResponse(
                1L,
                "Learn Testing",
                TodoStatus.OPEN,
                LocalDateTime.now()
        );

        when(todoService.getAllTodos())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Learn Testing"));
    }

    @Test
    void shouldReturnTodoById() throws Exception {

        TodoResponse response = new TodoResponse(
                1L,
                "Learn Mockito",
                TodoStatus.OPEN,
                LocalDateTime.now()
        );

        when(todoService.getTodoById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("Learn Mockito"));
    }

    @Test
    void shouldDeleteTodo() throws Exception {

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Todo deleted successfully"));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank()
            throws Exception {

        String requestBody = """
                {
                    "title": ""
                }
                """;

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Title is required"));
    }

    @Test
    void shouldReturnNotFoundWhenTodoDoesNotExist()
            throws Exception {

        when(todoService.getTodoById(999L))
                .thenThrow(
                        new TodoNotFoundException(
                                "Todo not found"
                        )
                );

        mockMvc.perform(get("/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Todo not found"));
    }
}