package com.moj.tasktracker.validation;

import com.moj.tasktracker.validation.constraint.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_REGEX =
      "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";

  private final Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

  @Override
  public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return pattern.matcher(email).matches();
  }
}
