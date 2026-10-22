package com.example.demo.task;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.example.demo.error.NotFoundExecption;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    // Concurent data type for handling concurrent requests in api (concurrent ->
    // multiple requests at once)
    // HashMap for storing the tasks in memory with integer Id and Task record
    // object
    private final Map<Integer, Task> tasks = new ConcurrentHashMap<Integer, Task>();
    // AtomicInteger variable for the autoincrement task id of the tasks
    private final AtomicInteger idGenerator = new AtomicInteger();

    // // Get all the task records
    // public List<Task> getAllTasks() {
    //     // get the values from the tasks hashmap convert to list and return it
    //     return tasks.values().stream().toList();    
    // }

    // Get all the task records with status filter
    public List<Task> getAllTasks(Boolean completed) {
        // get the values from the tasks hashmap convert to list and return it
        return tasks.values().stream().filter(val -> completed == null || val.isCompleted() == completed)
                .sorted(Comparator.comparing(Task::getId)).toList();
    }

    // Get Task by taskId
    public Task getTask(Integer taskId) {
        // get the task with asked taskId from the tasks hashmap
        Task foundTask = tasks.get(taskId);
        if (foundTask != null) {
            // task was found
            return foundTask;
        } else {
            // throw task not found error
            throw new NotFoundExecption("Task with Id " + taskId + " Not Found !!");
        }
    }

    // Create a task
    public Task createTask(TaskRequest taskRequest) {
        // create new task record
        Task newTask = new Task(idGenerator.incrementAndGet(), taskRequest.title(), taskRequest.description(),
                taskRequest.isCompleted(), LocalDateTime.now(), taskRequest.completedAt());
        // put the new task record in the hash map and return it
        tasks.put(newTask.getId(), newTask);
        return newTask;
    }

    // Update Task
    public Task updateTask(Integer taskId, TaskRequest taskRequest) {
        Task targetTask = getTask(taskId); // check if the task with asked taskId exists or not, if not exitst this
                                           // methods
        // throws erorr

        // make a new Task recerd updatedTask with old taskId and new details from
        // taskRequest
        targetTask.setCompleted(taskRequest.isCompleted());
        targetTask.setCompletedAt(taskRequest.completedAt());
        targetTask.setCreatedAt(taskRequest.createdAt());
        targetTask.setDescription(taskRequest.description());
        targetTask.setTitle(taskRequest.title()); // put new Task record updatedTask in the tasks HashMap with old
                                                  // taskId and
        // return it
        tasks.put(taskId, targetTask);

        return targetTask;
    }

    // delete a Task
    public void deleteTask(Integer taskId) {
        // check if task exists or not, same as the update task method above
        getTask(taskId);
        // remove the task with asked taskId from the tasks HashMap
        tasks.remove(taskId);
    }

    // toggle completion status of a task
    public void toggleTask(Integer taskId) {
        Task targetTask = getTask(taskId);
        if (targetTask != null) {
            targetTask.setCompleted(!targetTask.isCompleted());
            if (targetTask.isCompleted()) {
                targetTask.setCompletedAt(LocalDateTime.now());
            }
            tasks.put(taskId, targetTask);
        }
    }
}
