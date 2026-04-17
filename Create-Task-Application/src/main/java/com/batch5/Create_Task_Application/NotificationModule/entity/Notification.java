package com.batch5.Create_Task_Application.NotificationModule.entity;

import com.batch5.Create_Task_Application.UserModule.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="notification")
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private int notificationId;

    public Notification(int notificationId, boolean isRead, User user, LocalDateTime dateTime, String text) {
        this.notificationId = notificationId;
        this.isRead = isRead;
        this.user = user;
        this.dateTime = dateTime;
        this.text = text;
    }

    @NotBlank
    @Column(nullable=false)
    private String text;

    @CreationTimestamp
    @Column(name="date_time",nullable=false,updatable = false)
    private LocalDateTime dateTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    @Column(name = "is_read")
    private boolean isRead = false;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification() {
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
