package com.batch5.Create_Task_Application.NotificationModule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.apache.catalina.User;

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

    private String text;
    @Column(name="user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private List<User> users;



}
