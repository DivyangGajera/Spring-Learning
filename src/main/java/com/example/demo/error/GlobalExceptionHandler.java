package com.example.demo.error;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// global exception handler to handle all api errors from a single class
@RestControllerAdvice 
public class GlobalExceptionHandler {
    // record class for sending api call error response
    public record ApiError(int statusCode, String message, Map<String, String> fields) {
    }

    // execption handler to handle task not found errors 
    @ExceptionHandler(NotFoundExecption.class)
    public ResponseEntity<ApiError> notFound(NotFoundExecption error) {
        // set status code 404 and send api call error record
        return ResponseEntity.status(404).body(new ApiError(404, error.getMessage(), null));
    }

    // exception to handle validation related errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalidRequest(MethodArgumentNotValidException error) {
        // get the error details, (ex. missing fields or invalid field values)
        Map<String, String> fields = error.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                f -> f.getField(),
                f -> f.getDefaultMessage(),
                (a, b) -> a));
                // set the response type bad request and send api call error record
        return ResponseEntity.badRequest().body(new ApiError(400, "Validation Error", fields)); 
    }
}
