package com.spring.security.springsecurity.resources;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import java.util.List;


@RestController
public class TodoResource {
    private Logger  logger = LoggerFactory.getLogger(getClass());
    public static final List<Todo> TODOS = List.of(
            new Todo("smk", "learn spring security"),
            new Todo("sms", "learn spring boot")
    );

    @GetMapping("/todos")
    public List<Todo> retreiveAllTodos(){
        return TODOS;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasAnyRole('USER') and #username==authentication.name")
    @PostAuthorize("returnObject.username == 'smk'")
    @RolesAllowed({"USER","ADMIN"})
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public Todo retreiveAllTodosSpecificUser(@PathVariable String username){
        return TODOS.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForSpecificUser(@PathVariable String username, @RequestBody Todo todo){
        logger.info("create {} for {}",todo,username);
    }

}
record Todo (String username,String description){}