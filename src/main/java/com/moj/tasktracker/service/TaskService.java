package com.moj.tasktracker.service;

import com.moj.tasktracker.api.model.Task;
import com.moj.tasktracker.dto.Task.TaskRequest;
import com.moj.tasktracker.dto.Task.TaskResponse;
import com.moj.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request) {
        Task entity = repository.save(buildTask(request));
        return buildTaskResponse(entity);
    }

    private Task buildTask(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .due(request.getDue())
                .status(String.valueOf(Task.Status.PENDING))
                .build();
    }

    private TaskResponse buildTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .due(task.getDue())
                .createdOn(task.getCreatedOn())
                .createdBy(task.getCreatedBy())
                .lastUpdatedOn(task.getLastUpdatedOn())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .build();
    }
}
