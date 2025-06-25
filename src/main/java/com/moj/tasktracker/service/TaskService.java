package com.moj.tasktracker.service;

import com.moj.tasktracker.api.model.Task;
import com.moj.tasktracker.dto.Task.TaskRequest;
import com.moj.tasktracker.dto.Task.TaskResponse;
import com.moj.tasktracker.error.exception.NotFoundException;
import com.moj.tasktracker.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class TaskService {

    private final TaskRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskResponse create(TaskRequest request) {
        Task entity = repository.save(mapToTaskEntity(request));
        return mapToTaskResponse(entity);
    }

    public TaskResponse retrieveById(Long id) {
        Task task = repository.findByIdAndDeletedOnIsNull(id).orElseThrow(() ->
                new NotFoundException("Task with ID " + id + " not found."));

        return mapToTaskResponse(task);
    }

    public Page<TaskResponse> retrieveAllTasks (String status, LocalDateTime dueStart, LocalDateTime dueEnd, String title, Pageable pageable) {
        Page<Task> taskPage = repository.retrieveAllTasks(status, dueStart, dueEnd, title, pageable);

        return taskPage.map(this::mapToTaskResponse);
    }

    @Transactional
    public void updateStatus(Long taskId, String newStatus) {
        if (repository.findByIdAndDeletedOnIsNull(taskId).isEmpty()) {
            throw new NotFoundException("Task with ID " + taskId + " not found");
        }
        repository.updateTaskStatus(taskId, newStatus);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Task not found");
        }
        repository.softDelete(id, LocalDateTime.now());
    }

    private Task mapToTaskEntity(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .due(request.getDue())
                .createdBy("System")
                .createdOn(LocalDateTime.now())
                .status(String.valueOf("PENDING"))
                .build();
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .due(String.valueOf(task.getDue()))
                .createdOn(String.valueOf(task.getCreatedOn()))
                .createdBy(task.getCreatedBy())
                .lastUpdatedOn(String.valueOf(task.getLastUpdatedOn()))
                .lastUpdatedBy(task.getLastUpdatedBy())
                .build();
    }

}
