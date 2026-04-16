package com.batch5.Create_Task_Application.NotificationModule.service;

import com.batch5.Create_Task_Application.NotificationModule.dto.GetNotificationsResponseDto;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

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
                saved.getUser().getUserId(),
                saved.isRead()
        );
    }
    public NotificationResponseDto getNotificationById(int id)
    {
        Notification notification=notificationRepository.findById(id).orElseThrow(()->new NotificationNotFoundException("Notfication Not Found"));

         return new NotificationResponseDto(notification.getText(),notification.getUser().getUserId(),notification.isRead());
    }

    public List<GetNotificationsResponseDto> getNotificationsByUserId(long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id " + userId
                ));
        List<Notification> notifications = notificationRepository.findByUser(user);
        List<GetNotificationsResponseDto> responseList = new ArrayList<>();

        for(Notification n:notifications)
        {
            responseList.add(new GetNotificationsResponseDto(n.getNotificationId(),n.getText(),n.getDateTime(),n.isRead()));
        }
        return responseList;
    }

    public void deleteNotificationById(int notificationId)
    {
        if(!notificationRepository.existsById(notificationId))
        {
            throw new NotificationNotFoundException("Notification Not Found");
        }
        notificationRepository.deleteById(notificationId);
    }

    public List<GetNotificationsResponseDto> getUnreadNotificationsByUserId(long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id " + userId
                ));

        List<Notification> notifications =
                notificationRepository.findByUserAndIsReadFalse(user.getUserId());

        List<GetNotificationsResponseDto> responseList = new ArrayList<>();

        for (Notification n : notifications) {
            responseList.add(
                    new GetNotificationsResponseDto(
                            n.getNotificationId(),
                            n.getText(),
                            n.getDateTime(),
                            n.isRead()
                    )
            );
        }

        return responseList;
    }

    public void markNotificationAsRead(int notificationId)
    {
        if(!notificationRepository.existsById(notificationId))
        {
            throw new NotificationNotFoundException("Notification Not Found");
        }
        Notification notification = notificationRepository.findById(notificationId).get();
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
