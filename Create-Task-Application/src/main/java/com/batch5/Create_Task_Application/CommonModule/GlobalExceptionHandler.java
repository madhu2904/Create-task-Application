package com.batch5.Create_Task_Application.CommonModule;

import com.batch5.Create_Task_Application.CollaborationModule.dto.ErrorDetails;
import com.batch5.Create_Task_Application.CollaborationModule.exceptions.*;
import com.batch5.Create_Task_Application.NotificationModule.exceptions.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    //  Not Found Exceptions of all modules
    @ExceptionHandler({
            UserNotFoundException.class,
            ProjectNotFoundException.class,
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

    // Bad request exceptions of all modules
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

    // conflicts ans pre-existing data exceptions
    @ExceptionHandler({
            UserAlreadyExistsException.class,
            DataConflictException.class,
            CategoryAlreadyAssignedException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(Exception ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // project search exception
    @ExceptionHandler(ProjectSearchException.class)
    public ResponseEntity<ErrorResponse> handleProjectSearch(ProjectSearchException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // data validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, errors);
    }

    // type mismatch exceptions
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST, message);
    }

    // validation of request
//    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationMap(MethodArgumentNotValidException ex) {
//
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        return build(HttpStatus.BAD_REQUEST, errors.toString());
//    }

    // generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // common error response builder
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }
}