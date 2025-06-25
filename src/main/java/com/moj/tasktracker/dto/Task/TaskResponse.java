package com.moj.tasktracker.dto.Task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

  private Long id;
  private String title;
  private String description;
  private String due;
  private String status;
  private String createdOn;
  private String createdBy;
  private String lastUpdatedOn;
  private String lastUpdatedBy;
}
