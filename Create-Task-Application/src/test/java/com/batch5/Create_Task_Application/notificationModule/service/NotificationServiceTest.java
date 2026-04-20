package com.batch5.Create_Task_Application.notificationModule.service;

import com.batch5.Create_Task_Application.notificationModule.dto.*;
import com.batch5.Create_Task_Application.notificationModule.entity.Notification;
import com.batch5.Create_Task_Application.notificationModule.exceptions.NotificationNotFoundException;
import com.batch5.Create_Task_Application.notificationModule.repository.NotificationRepository;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.exceptions.UserNotFoundException;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl service;

    //  Create User
    private User getUser() {
        User user = new User();
        user.setUserId(1L); // must exist in your entity
        return user;
    }

    //  Create Notification
    private Notification getNotification(User user) {
        Notification n = new Notification();
        n.setNotificationId(1);
        n.setText("Hello");
        n.setUser(user);
        n.setRead(false);
        n.setDateTime(LocalDateTime.now());
        return n;
    }


    // POSITIVE TEST CASES

    @Test
    void shouldAddNotification() {
        User user = getUser();

        NotificationRequestDto dto = new NotificationRequestDto();
        dto.setUserId(1L);
        dto.setText("Hello");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(getNotification(user));

        NotificationResponseDto response = service.addNotification(dto);

        assertEquals("Notification Sent Successfully", response.getText());
    }

    @Test
    void shouldGetNotificationById() {
        User user = getUser();
        Notification n = getNotification(user);

        when(notificationRepository.findById(1)).thenReturn(Optional.of(n));

        NotificationResponseDto response = service.getNotificationById(1);

        assertEquals("Hello", response.getText());
    }

    @Test
    void shouldGetNotificationsByUserId() {
        User user = getUser();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.findByUser(user))
                .thenReturn(List.of(getNotification(user)));

        List<GetNotificationsResponseDto> list =
                service.getNotificationsByUserId(1L);

        assertFalse(list.isEmpty());
    }

    @Test
    void shouldDeleteNotification() {
        when(notificationRepository.existsById(1)).thenReturn(true);
        doNothing().when(notificationRepository).deleteById(1);

        service.deleteNotificationById(1);

        verify(notificationRepository).deleteById(1);
    }

    @Test
    void shouldMarkNotificationAsRead() {
        User user = getUser();
        Notification n = getNotification(user);

        when(notificationRepository.existsById(1)).thenReturn(true);
        when(notificationRepository.findById(1)).thenReturn(Optional.of(n));

        service.markNotificationAsRead(1);

        assertTrue(n.isRead());
    }

    // NEGATIVE TEST CASES
    @Test
    void shouldThrowUserNotFoundInAdd() {
        NotificationRequestDto dto = new NotificationRequestDto();
        dto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.addNotification(dto));
    }

    @Test
    void shouldThrowNotificationNotFound() {
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class,
                () -> service.getNotificationById(1));
    }

    @Test
    void shouldThrowUserNotFoundInGetAll() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> service.getNotificationsByUserId(1L));
    }

    @Test
    void shouldThrowExceptionWhenDeletingInvalid() {
        when(notificationRepository.existsById(1)).thenReturn(false);

        assertThrows(NotificationNotFoundException.class,
                () -> service.deleteNotificationById(1));
    }

    @Test
    void shouldThrowExceptionWhenMarkReadInvalid() {
        when(notificationRepository.existsById(1)).thenReturn(false);

        assertThrows(NotificationNotFoundException.class,
                () -> service.markNotificationAsRead(1));
    }
}