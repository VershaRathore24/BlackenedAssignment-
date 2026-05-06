package com.example.assignment.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Scheduled(fixedRate = 300000)
    public void sweepNotifications() {

        System.out.println("Checking pending notifications...");
    }
}