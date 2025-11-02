package com.gaurav.springboot.todos.service;

import com.gaurav.springboot.todos.entity.Todo;
import com.gaurav.springboot.todos.entity.User;
import com.gaurav.springboot.todos.repository.TodoRepository;
import com.gaurav.springboot.todos.request.TodoRequest;
import com.gaurav.springboot.todos.request.TodoResponse;
import com.gaurav.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final FindAuthenticatedUser findAuthenticatedUser;
    private final TodoRepository todoRepository;

    public TodoServiceImpl(FindAuthenticatedUser findAuthenticatedUser, TodoRepository todoRepository) {
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.todoRepository = todoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TodoResponse> getAllTodos() {
        List<Todo> todos = todoRepository.findByOwner(findAuthenticatedUser.getAuthenticatedUser());
        return todos.stream().map(this::convertTodoToTodoResponse).toList();
    }

    @Transactional
    @Override
    public List<TodoResponse> createTodo(List<TodoRequest> requests) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        List<TodoResponse> todoResponses = new ArrayList<>();
        for (TodoRequest todoRequest : requests) {
            Todo todo = convertTodoRequestToTodo(todoRequest, currentUser);
            todoResponses.add(convertTodoToTodoResponse(todoRepository.save(todo)));
        }
        return todoResponses;
    }

    @Transactional
    @Override
    public TodoResponse toggleTodoCompletion(long id) {
        User currentUser = findAuthenticatedUser.getAuthenticatedUser();
        Optional<Todo> todoOptional = todoRepository.findByIdAndOwner(id, currentUser);
        if (todoOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        Todo todo = todoOptional.get();
        todo.setComplete(!todo.isComplete());

        return convertTodoToTodoResponse(todoRepository.save(todo));
    }

    private Todo convertTodoRequestToTodo(TodoRequest todoRequest, User currentUser) {
        return new Todo(
                todoRequest.getTitle(),
                todoRequest.getDescription(),
                todoRequest.getPriority(),
                false,
                currentUser);
    }

    private TodoResponse convertTodoToTodoResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getPriority(),
                todo.isComplete()
        );
    }
}
