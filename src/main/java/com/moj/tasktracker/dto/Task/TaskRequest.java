package com.moj.tasktracker.dto.Task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotNull(message = "required")
    @Size(min = 3, max = 100, message = "{length.range}")
    private String title;

    @Size(min = 3, max = 200, message = "{length.range}")
    private String description;

    @NotNull(message = "required")
    private LocalDateTime due;
}
