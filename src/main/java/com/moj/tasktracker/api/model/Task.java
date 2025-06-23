package com.moj.tasktracker.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "tasks")
public class Task extends BaseModel {
    private String title;
    private String description;
    private LocalDateTime due;
    private String status;

    public enum Status {
        PENDING, COMPLETED
    }
}
