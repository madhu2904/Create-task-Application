package com.batch5.Create_Task_Application.CollaborationModule.exceptions;

import com.batch5.Create_Task_Application.CollaborationModule.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

        // Handle specific exceptions
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(AttachmentNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleAttachmentNotFoundException(AttachmentNotFoundException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(TaskNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleTaskNotFoundException(TaskNotFoundException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(CommentNotFoundException.class)
        public ResponseEntity<ErrorDetails> handleCommentNotFoundException(CommentNotFoundException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(AttachmentStorageException.class)
        public ResponseEntity<ErrorDetails> handleAttachmentStorageException(AttachmentStorageException exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        // Handle Validation Exceptions
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException exception,
                        WebRequest webRequest) {
                String defaultMessage = exception.getBindingResult().getFieldError() != null
                                ? exception.getBindingResult().getFieldError().getDefaultMessage()
                                : "Validation Error";
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), defaultMessage,
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }

        // Handle Global Exceptions
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                        WebRequest webRequest) {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                                webRequest.getDescription(false));
                return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
