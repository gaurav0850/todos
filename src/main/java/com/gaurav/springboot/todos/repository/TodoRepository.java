package com.gaurav.springboot.todos.repository;

import com.gaurav.springboot.todos.entity.Todo;
import com.gaurav.springboot.todos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.owner = :user")
    List<Todo> findTodosByUser(@Param("user") User user);
}
