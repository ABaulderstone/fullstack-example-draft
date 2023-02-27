package io.nology.fullstackexample.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handle our own exceptions
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  // Or existing ones
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<FieldErrorMap> handleValidationExceptions(
    MethodArgumentNotValidException exception
  ) {
    FieldErrorMap errorMap = new FieldErrorMap(
      exception.getBindingResult().getFieldErrors()
    );
    return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
  }

  // helpers for dealing with error formatting can go here or in a seperate file

  class FieldErrorMap {

    private Map<String, List<String>> errors;

    public FieldErrorMap(List<FieldError> fieldErrors) {
      this.errors = new HashMap<>();
      for (FieldError error : fieldErrors) {
        addError(error.getField(), error.getDefaultMessage());
      }
    }

    private void addError(String fieldName, String errorMessage) {
      List<String> fieldErrors = errors.getOrDefault(
        fieldName,
        new ArrayList<>()
      );
      fieldErrors.add(errorMessage);
      errors.put(fieldName, fieldErrors);
    }

    public Map<String, List<String>> getErrors() {
      return errors;
    }
  }
}
