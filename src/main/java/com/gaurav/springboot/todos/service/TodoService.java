package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.request.TodoRequest;
import com.gaurav.springboot.todos.request.TodoResponse;

import java.util.List;

public interface TodoService {
    List<TodoResponse> getAllTodos();

    List<TodoResponse> createTodo(List<TodoRequest> request);

    TodoResponse toggleTodoCompletion(long id);

    void deleteTodo(long id);
}
