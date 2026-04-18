package com.batch5.Create_Task_Application.CommonModule;

import com.batch5.Create_Task_Application.CollaborationModule.exceptions.*;
import com.batch5.Create_Task_Application.NotificationModule.exceptions.*;
import com.batch5.Create_Task_Application.ProjectModule.exceptions.*;
import com.batch5.Create_Task_Application.TaskModule.exceptions.*;
import com.batch5.Create_Task_Application.UserModule.exceptions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler({
            UserNotFoundException.class,
            TaskNotFoundException.class,
            NotificationNotFoundException.class,
            ResourceNotFoundException.class,
            AttachmentNotFoundException.class,
            CommentNotFoundException.class,
            CategoryNotFoundException.class,
            NoDataFoundException.class,
            ProjectNotFoundException.class,
            ProjectSearchException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleNotFound(RuntimeException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400 - Bad Request
    @ExceptionHandler({
            InvalidUserDataException.class,
            InvalidTaskException.class,
            InvalidCategoryException.class,
            CategoryNotMappedException.class,
            IllegalArgumentException.class,
            AttachmentStorageException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 - Malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Malformed JSON request. Please check your input.");
    }

    // 409 - Conflict
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            DataConflictException.class,
            CategoryAlreadyAssignedException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleConflict(Exception ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 400 - Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, errors);
    }

    // 400 - Type Mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value '" + ex.getValue() +
                "' for parameter '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST, message);
    }

    // 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobal(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Common builder
    private ResponseEntity<ApiResponse<Object>> build(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new ApiResponse<>(status.value(), message, null),
                status
        );
    }
}