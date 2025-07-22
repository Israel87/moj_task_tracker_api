package com.moj.tasktracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moj.tasktracker.api.model.Task;
import com.moj.tasktracker.dto.Task.TaskRequest;
import com.moj.tasktracker.dto.Task.TaskResponse;
import com.moj.tasktracker.error.exception.NotFoundException;
import com.moj.tasktracker.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class TaskServiceTest {

  private TaskService service;
  private TaskRepository repository;

  @BeforeEach
  void setUp() {
    repository = mock(TaskRepository.class);
    service = new TaskService(repository);
  }

  @Test
  void create_ShouldSaveTaskAndReturnResponse() {
    TaskRequest request = new TaskRequest();
    request.setTitle("Test Task");
    request.setDescription("Test Description");
    request.setDue(LocalDateTime.now());

    Task savedTask = Task.builder()
        .id(1L)
        .title("Test Task")
        .description("Test Description")
        .due(request.getDue())
        .status("PENDING")
        .createdBy("System")
        .createdOn(LocalDateTime.now())
        .build();

    when(repository.save(any(Task.class))).thenReturn(savedTask);

    TaskResponse response = service.create(request);

    assertEquals("Test Task", response.getTitle());
    assertEquals("PENDING", response.getStatus());
    assertNotNull(response.getCreatedOn());
  }

  @Test
  void retrieveById_ShouldReturnTask_WhenExists() {
    Task task = Task.builder()
        .id(1L)
        .title("Sample Task")
        .status("PENDING")
        .createdBy("System")
        .createdOn(LocalDateTime.now())
        .build();

    when(repository.findByIdAndDeletedOnIsNull(1L)).thenReturn(Optional.of(task));

    TaskResponse response = service.retrieveById(1L);

    assertEquals("Sample Task", response.getTitle());
    assertEquals("PENDING", response.getStatus());
  }

  @Test
  void retrieveById_ShouldThrow_WhenNotFound() {
    when(repository.findByIdAndDeletedOnIsNull(100L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> service.retrieveById(100L));
  }

  @Test
  void retrieveAllTasks_ShouldReturnPagedTasks() {
    Task task = Task.builder()
        .id(1L)
        .title("Test Task")
        .description("desc")
        .status("PENDING")
        .createdOn(LocalDateTime.now())
        .build();

    Page<Task> page = new PageImpl<>(Collections.singletonList(task));

    when(repository.retrieveAllTasks(any(), any(), any(), any(), any())).thenReturn(page);

    Page<TaskResponse> result = service.retrieveAllTasks(null, null, null, null,
        PageRequest.of(0, 10));

    assertEquals(1, result.getTotalElements());
    assertEquals("Test Task", result.getContent().get(0).getTitle());
  }

  @Test
  void updateStatus_ShouldUpdate_WhenTaskExists() {
    Task task = Task.builder()
        .id(1L)
        .title("Update Me")
        .status("PENDING")
        .createdOn(LocalDateTime.now())
        .build();

    when(repository.findByIdAndDeletedOnIsNull(1L)).thenReturn(Optional.of(task));

    service.updateStatus(1L, "COMPLETED");

    verify(repository).updateTaskStatus(1L, "COMPLETED");
  }

  @Test
  void updateStatus_ShouldThrow_WhenTaskNotFound() {
    when(repository.findByIdAndDeletedOnIsNull(100L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> service.updateStatus(100L, "IN_PROGRESS"));
  }

  @Test
  void deleteTask_ShouldSoftDelete_WhenExists() {
    when(repository.existsById(1L)).thenReturn(true);

    service.deleteTask(1L);

    verify(repository).softDelete(eq(1L), any(LocalDateTime.class));
  }

  @Test
  void deleteTask_ShouldThrow_WhenNotExists() {
    when(repository.existsById(100L)).thenReturn(false);

    assertThrows(NotFoundException.class, () -> service.deleteTask(100L));
  }
}
