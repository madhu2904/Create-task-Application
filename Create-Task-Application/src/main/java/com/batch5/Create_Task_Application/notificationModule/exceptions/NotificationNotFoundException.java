package com.batch5.Create_Task_Application.notificationModule.exceptions;

import com.batch5.Create_Task_Application.commonModule.exceptions.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
