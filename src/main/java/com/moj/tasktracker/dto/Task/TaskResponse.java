package com.moj.tasktracker.dto.Task;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime due;
    private String status;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastUpdatedOn;
    private String lastUpdatedBy;
}
