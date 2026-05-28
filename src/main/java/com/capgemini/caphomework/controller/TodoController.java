package com.capgemini.caphomework.controller;

import com.capgemini.caphomework.dto.CreateTodoRequest;
import com.capgemini.caphomework.dto.TodoResponse;
import com.capgemini.caphomework.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponse createTodo(
            @Valid @RequestBody CreateTodoRequest request) {

        return todoService.createTodo(request);
    }

    @GetMapping
    public List<TodoResponse> getAllTodos() {

        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public TodoResponse getTodoById(@PathVariable Long id) {

        return todoService.getTodoById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id) {

        todoService.deleteTodo(id);

        return "Todo deleted successfully";
    }
}