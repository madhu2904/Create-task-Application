package com.batch5.Create_Task_Application.CommonModule;

import com.batch5.Create_Task_Application.CollaborationModule.exceptions.*;
import com.batch5.Create_Task_Application.NotificationModule.exceptions.*;
import com.batch5.Create_Task_Application.ProjectModule.dto.ApiResponse;
import com.batch5.Create_Task_Application.ProjectModule.exceptions.*;
import com.batch5.Create_Task_Application.TaskModule.exceptions.*;
import com.batch5.Create_Task_Application.UserModule.exceptions.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────────
    // PROJECT MODULE — uses ApiResponse structure
    // ─────────────────────────────────────────────

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleProjectNotFound(ProjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, ex.getMessage(), null));
    }

    @ExceptionHandler(ProjectSearchException.class)
    public ResponseEntity<ApiResponse<Object>> handleProjectSearch(ProjectSearchException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, ex.getMessage(), null));
    }

    // ─────────────────────────────────────────────
    // OTHER MODULES — use original ErrorResponse
    // ─────────────────────────────────────────────

    @ExceptionHandler({
            UserNotFoundException.class,
            TaskNotFoundException.class,
            NotificationNotFoundException.class,
            ResourceNotFoundException.class,
            AttachmentNotFoundException.class,
            CommentNotFoundException.class,
            CategoryNotFoundException.class,
            NoDataFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            InvalidUserDataException.class,
            InvalidTaskException.class,
            InvalidCategoryException.class,
            CategoryNotMappedException.class,
            IllegalArgumentException.class,
            AttachmentStorageException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            DataConflictException.class,
            CategoryAlreadyAssignedException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(Exception ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(message, LocalDateTime.now());
        return new ResponseEntity<>(error, status);
    }
}