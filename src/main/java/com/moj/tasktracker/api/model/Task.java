package com.moj.tasktracker.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private LocalDateTime due;
  private String status;
  private LocalDateTime createdOn;
  private String createdBy;
  private LocalDateTime lastUpdatedOn;
  private String lastUpdatedBy;
  private LocalDateTime deletedOn;
  private String deletedBy;

  public enum Status {
    COMPLETED, DELETED
  }
}
