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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notification")
public class Notification
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private int notificationId;

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

}
