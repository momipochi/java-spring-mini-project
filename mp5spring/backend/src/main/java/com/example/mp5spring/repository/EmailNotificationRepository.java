package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.EmailNotification;

public interface EmailNotificationRepository extends CrudRepository<EmailNotification, Long> {
}
