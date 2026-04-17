package com.batch5.Create_Task_Application.NotificationModule.dto;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseStructureDto<T>
{
    int status;
    String message;
    T data;
    LocalDateTime timeStamp;

    public ResponseStructureDto(int status, String message, T data, LocalDateTime timeStamp) {
        this.status = status;
        this.timeStamp = timeStamp;
        this.data = data;

        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }



}
