package com.capgemini.caphomework.service;

import com.capgemini.caphomework.dto.CreateTodoRequest;
import com.capgemini.caphomework.dto.TodoResponse;
import com.capgemini.caphomework.entity.Todo;
import com.capgemini.caphomework.entity.TodoStatus;
import com.capgemini.caphomework.exception.TodoNotFoundException;
import com.capgemini.caphomework.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldCreateTodo() {

        CreateTodoRequest request = new CreateTodoRequest();

        request.setTitle("Learn Spring Boot");

        Todo savedTodo = Todo.builder()
                .id(1L)
                .title("Learn Spring Boot")
                .status(TodoStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        when(todoRepository.save(any(Todo.class)))
                .thenReturn(savedTodo);

        TodoResponse response = todoService.createTodo(request);

        assertNotNull(response);

        assertEquals("Learn Spring Boot",
                response.getTitle());

        assertEquals(TodoStatus.OPEN,
                response.getStatus());

        verify(todoRepository, times(1))
                .save(any(Todo.class));
    }

    @Test
    void shouldReturnAllTodos() {

        Todo todo = Todo.builder()
                .id(1L)
                .title("Learn Spring Boot")
                .status(TodoStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        when(todoRepository.findAll())
                .thenReturn(List.of(todo));

        List<TodoResponse> result =
                todoService.getAllTodos();

        assertEquals(1, result.size());

        assertEquals("Learn Spring Boot",
                result.get(0).getTitle());
    }

    @Test
    void shouldReturnTodoById() {

        Todo todo = Todo.builder()
                .id(1L)
                .title("Learn Spring Boot")
                .status(TodoStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        when(todoRepository.findById(1L))
                .thenReturn(Optional.of(todo));

        TodoResponse result =
                todoService.getTodoById(1L);

        assertNotNull(result);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenTodoNotFound() {

        when(todoRepository.findById(1L))
                .thenReturn(Optional.empty());

        TodoNotFoundException exception =
                assertThrows(
                        TodoNotFoundException.class,
                        () -> todoService.getTodoById(1L)
                );

        assertEquals("Todo not found",
                exception.getMessage());
    }

    @Test
    void shouldDeleteTodo() {

        when(todoRepository.existsById(1L))
                .thenReturn(true);

        todoService.deleteTodo(1L);

        verify(todoRepository, times(1))
                .deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingMissingTodo() {

        when(todoRepository.existsById(1L))
                .thenReturn(false);

        TodoNotFoundException exception =
                assertThrows(
                        TodoNotFoundException.class,
                        () -> todoService.deleteTodo(1L)
                );

        assertEquals("Todo not found",
                exception.getMessage());
    }
}