package com.example.demo.task;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    // make a private Task Service object
    private final TaskService taskService;

    // make public constructor of the class that helps make transfer object
    // reference local to global for dependency injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // make a get api endpoint for returning all the task records at once
    @GetMapping(params = "done")
    public List<Task> getAll(@RequestParam(required = false) Boolean done) {
        return taskService.getAllTasks(done);
    }


    // make a get api endpoint for returning all the task records at once
    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAllTasks(null);
    }

    // make a get api with /<taskId> to return the task details on that id
    @GetMapping("/{id}")
    public Task getOne(@PathVariable Integer id) {
        return taskService.getTask(id);
    }

    // make a post api with /create endpoint to create a task
    @PostMapping("/create")
    public ResponseEntity<Task> createOneTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return  ResponseEntity.created(URI.create("api/tasks/"+createdTask.getId())).body(createdTask);
    }

    // make a put api with /<taskId> endpoint to edit a task
    @PutMapping("/{id}")
    public Task updateOneTask(@PathVariable  Integer id, @Valid @RequestBody TaskRequest taskRequest){
        return taskService.updateTask(id, taskRequest);
    }


    // make a delete api with /<taskId> endpoint to delete a task 
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneTask(@PathVariable Integer id){
        taskService.deleteTask(id);
    }

    
    // toggle the task status of task based on id
    @PutMapping ("/toogle/{id}")
    public  void toggleTaskStatus(@PathVariable  Integer id){
        taskService.toggleTask(id);
    }
}
