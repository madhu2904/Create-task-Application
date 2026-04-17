package com.batch5.Create_Task_Application.commonModule;

import com.batch5.Create_Task_Application.collaborationModule.exceptions.*;
import com.batch5.Create_Task_Application.notificationModule.exceptions.*;
import com.batch5.Create_Task_Application.projectModule.exceptions.*;
import com.batch5.Create_Task_Application.taskModule.exceptions.*;
import com.batch5.Create_Task_Application.userModule.exceptions.*;

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

    //Not Found (404)
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
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {

        return build(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON request. Please check your input."
        );
    }


    // Bad Request (400)
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

    // Conflict (409)
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            DataConflictException.class,
            CategoryAlreadyAssignedException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(Exception ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }


    // Validation (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, errors);
    }

    // Type Mismatch (400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String message = "Invalid value '" + ex.getValue() +
                "' for parameter '" + ex.getName() + "'";

        return build(HttpStatus.BAD_REQUEST, message);
    }

    // Global Error (500)

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Builder Method
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {

        ErrorResponse error = new ErrorResponse(
                status.value(),
                message
        );

        return new ResponseEntity<>(error, status);
    }
}