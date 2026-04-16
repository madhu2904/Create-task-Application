package com.batch5.Create_Task_Application.NotificationModule.service;

import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationRequestDto;
import com.batch5.Create_Task_Application.NotificationModule.dto.NotificationResponseDto;
import com.batch5.Create_Task_Application.NotificationModule.entity.Notification;
import com.batch5.Create_Task_Application.NotificationModule.exceptions.NotificationNotFoundException;
import com.batch5.Create_Task_Application.NotificationModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.NotificationModule.repository.NotificationRepository;
import com.batch5.Create_Task_Application.UserModule.entity.User;
import com.batch5.Create_Task_Application.UserModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationResponseDto addNotification(NotificationRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id " + dto.getUserId()
                ));

        Notification notification = new Notification();
        notification.setText(dto.getText());
        notification.setUser(user);

        Notification saved = notificationRepository.save(notification);

        return new NotificationResponseDto(
                "Notification Sent Successfully",
                saved.getUser().getUserId()
        );
    }


    public NotificationResponseDto getNotification(int id)
    {
        Notification notification=notificationRepository.findById(id).orElseThrow(()->new NotificationNotFoundException("Notfication Not Found"));

         return new NotificationResponseDto(notification.getText(),notification.getUser().getUserId());
    }

}
