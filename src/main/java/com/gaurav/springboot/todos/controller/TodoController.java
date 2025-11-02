package com.gaurav.springboot.todos.controller;

import com.gaurav.springboot.todos.request.TodoRequest;
import com.gaurav.springboot.todos.request.TodoResponse;
import com.gaurav.springboot.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Todo REST Endpoints", description = "Operations related to todo of current user")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Get todos", description = "Get all todos for current user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getTodos() {
        return todoService.getAllTodos();
    }

    @Operation(summary = "Create todo", description = "Crate a new todo for current user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public List<TodoResponse> createTodo(@Valid @RequestBody List<TodoRequest> todoRequests) {
        return todoService.createTodo(todoRequests);
    }

    @Operation(summary = "Toggle todo completion", description = "Toggle Todo completion")
    @PutMapping("/todo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoResponse toggleTodoCompletion(@PathVariable("id") @Min(1) long id) {
        return todoService.toggleTodoCompletion(id);
    }

    @Operation(summary = "Delete todo", description = "Delete todo for signed in user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable("id") @Min(1) long id) {
        todoService.deleteTodo(id);
    }
}
