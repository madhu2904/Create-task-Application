package com.batch5.Create_Task_Application.notificationModule.repository;

import com.batch5.Create_Task_Application.notificationModule.entity.Notification;
import com.batch5.Create_Task_Application.userModule.entity.User;
import com.batch5.Create_Task_Application.userModule.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ FIXED: Proper User creation (ALL required fields)
    private User createUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@gmail.com");
        user.setFullName("Test User");

        return userRepository.save(user);
    }

    // Helper to create valid notification
    private Notification createNotification(User user, boolean isRead) {
        Notification n = new Notification();
        n.setText("Test Message");
        n.setUser(user);
        n.setRead(isRead);

        return notificationRepository.save(n);
    }


    //  POSITIVE TEST CASES
    @Test
    @DisplayName("Save Notification")
    void shouldSaveNotification() {
        User user = createUser();

        Notification n = createNotification(user, false);

        assertNotNull(n.getNotificationId());
        assertEquals("Test Message", n.getText());
    }

    @Test
    @DisplayName("Find by User")
    void shouldFindByUser() {
        User user = createUser();
        createNotification(user, false);

        List<Notification> list = notificationRepository.findByUser(user);

        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Find unread notifications")
    void shouldFindUnreadNotifications() {
        User user = createUser();
        createNotification(user, false);

        List<Notification> list =
                notificationRepository.findByUserAndIsReadFalse(user.getUserId());

        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Return empty when no notifications")
    void shouldReturnEmptyList() {
        User user = createUser();

        List<Notification> list = notificationRepository.findByUser(user);

        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Return empty unread list when all are read")
    void shouldReturnEmptyUnreadList() {
        User user = createUser();
        createNotification(user, true);

        List<Notification> list =
                notificationRepository.findByUserAndIsReadFalse(user.getUserId());

        assertTrue(list.isEmpty());
    }


    // NEGATIVE TEST CASES
    @Test
    @DisplayName("Should fail when saving notification without user")
    void shouldFailWhenUserIsNull() {

        Notification notification = new Notification();
        notification.setText("Test Message");
        notification.setUser(null); // ❌ invalid

        assertThrows(Exception.class, () -> {
            notificationRepository.saveAndFlush(notification);
        });
    }

    @Test
    @DisplayName("Should fail when text is null")
    void shouldFailWhenTextIsNull() {

        User user = createUser();

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setText(null); // ❌ invalid

        assertThrows(Exception.class, () -> {
            notificationRepository.saveAndFlush(notification);
        });
    }

    @Test
    @DisplayName("Should return empty for invalid userId in unread query")
    void shouldReturnEmptyForInvalidUserId() {

        List<Notification> list =
                notificationRepository.findByUserAndIsReadFalse(999L);

        assertTrue(list.isEmpty());
    }
}