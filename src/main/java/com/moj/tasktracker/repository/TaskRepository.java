package com.moj.tasktracker.repository;

import com.moj.tasktracker.api.model.Task;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends Repository<Task, Integer> {

  Task save(Task entity);

  boolean existsById(Long id);

  Optional<Task> findByIdAndDeletedOnIsNull(Long id);

  @Query("""
          SELECT t FROM Task t
          WHERE (:status IS NULL OR LOWER(t.status) LIKE LOWER(CONCAT('%', :status, '%')))
            AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:dueStart IS NULL OR t.due >= :dueStart)
            AND (:dueEnd IS NULL OR t.due <= :dueEnd)
            AND t.deletedOn IS NULL
      """)
  Page<Task> retrieveAllTasks(
      @Param("status") String status,
      @Param("dueStart") LocalDateTime dueStart,
      @Param("dueEnd") LocalDateTime dueEnd,
      @Param("title") String title,
      Pageable pageable
  );

  @Modifying
  @Query("UPDATE Task t SET t.status = :status, t.lastUpdatedOn = CURRENT_TIMESTAMP, t.lastUpdatedBy = 'System' WHERE t.id = :id")
  void updateTaskStatus(@Param("id") Long id, @Param("status") String status);

  @Modifying
  @Transactional
  @Query("UPDATE Task t SET t.deletedOn = :deletedOn WHERE t.id = :id")
  void softDelete(@Param("id") Long id, @Param("deletedOn") LocalDateTime deletedOn);

}
