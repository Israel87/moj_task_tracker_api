package com.moj.tasktracker.api.controller;

import com.moj.tasktracker.dto.Task.TaskRequest;
import com.moj.tasktracker.dto.Task.TaskResponse;
import com.moj.tasktracker.dto.Task.UpdateStatusRequest;
import com.moj.tasktracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Tasks")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

  private final TaskService taskService;

  @Operation(summary = "Create Task", description = "create a task to track")
  @PostMapping
  public ResponseEntity<TaskResponse> create(@RequestBody @Validated TaskRequest request) {
    TaskResponse response = taskService.create(request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Retrieve Task", description = "retrieve a task by ID")
  @GetMapping("/{id}")
  public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
    TaskResponse response = taskService.retrieveById(id);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Retrieve Tasks", description = "retrieve a paginated list of tasks")
  @GetMapping("/retrieve")
  public ResponseEntity<Page<TaskResponse>> retrieveTasks(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueStart,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueEnd,
      @RequestParam(required = false) String title,
      Pageable pageable) {

    LocalDateTime start = (dueStart != null) ? dueStart.atStartOfDay() : null;
    LocalDateTime end = (dueEnd != null) ? dueEnd.atTime(23, 59, 59) : null;
    Page<TaskResponse> response = taskService.retrieveAllTasks(status, start, end, title, pageable);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Update Status", description = "update the status of an existing task")
  @PatchMapping("/{id}/status")
  public ResponseEntity<String> updateStatus(@PathVariable Long id,
      @RequestBody UpdateStatusRequest request) {
    taskService.updateStatus(id, String.valueOf(request.getStatus()));
    return ResponseEntity.ok("Status updated successfully");
  }

  @Operation(summary = "Delete Task", description = "delete an existing task")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }

}
