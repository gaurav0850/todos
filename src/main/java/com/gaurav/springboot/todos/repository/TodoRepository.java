package com.gaurav.springboot.todos.repository;

import com.gaurav.springboot.todos.entity.Todo;
import com.gaurav.springboot.todos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);

    Optional<Todo> findByIdAndOwner(long id, User owner);
}
