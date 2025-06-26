package com.moj.tasktracker.dto.Task;

import com.moj.tasktracker.api.model.Task.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

  private Status status;
}
