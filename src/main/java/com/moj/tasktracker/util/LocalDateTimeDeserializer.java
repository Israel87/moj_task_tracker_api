package com.moj.tasktracker.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String text = p.getText().trim();
    if (text.length() == 10) { // e.g. "2025-06-24"
      LocalDate date = LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
      return date.atStartOfDay(); // convert to LocalDateTime
    } else {
      return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
  }
}
