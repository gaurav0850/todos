package com.gaurav.springboot.todos.repository;

import com.gaurav.springboot.todos.entity.Todo;
import com.gaurav.springboot.todos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);
}
