package com.moj.tasktracker.repository;

import com.moj.tasktracker.api.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends Repository<Task, Integer> {
    Task save(Task entity);

    Optional<Task> findById(Long id);

    void delete(Task entity);

    Page<Task> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    void updateTaskStatus(@Param("id") Long id, @Param("status") String status);

}
