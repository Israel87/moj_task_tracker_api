package com.moj.tasktracker.dto.Task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moj.tasktracker.util.LocalDateTimeDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskRequest {

  @NotNull(message = "required")
  @Size(min = 3, max = 100, message = "{length.range}")
  private String title;

  @Size(min = 3, max = 200, message = "{length.range}")
  private String description;

  @NotNull(message = "required")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime due;
}
