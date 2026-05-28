package com.capgemini.caphomework.service;

import com.capgemini.caphomework.dto.CreateTodoRequest;
import com.capgemini.caphomework.dto.TodoResponse;
import com.capgemini.caphomework.entity.Todo;
import com.capgemini.caphomework.entity.TodoStatus;
import com.capgemini.caphomework.exception.TodoNotFoundException;
import com.capgemini.caphomework.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponse createTodo(CreateTodoRequest request) {

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .status(TodoStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        Todo savedTodo = todoRepository.save(todo);

        return mapToResponse(savedTodo);
    }

    public List<TodoResponse> getAllTodos() {

        return todoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TodoResponse getTodoById(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new TodoNotFoundException("Todo not found"));

        return mapToResponse(todo);
    }

    public void deleteTodo(Long id) {

        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found");
        }

        todoRepository.deleteById(id);
    }

    private TodoResponse mapToResponse(Todo todo) {

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getStatus(),
                todo.getCreatedAt()
        );
    }
}