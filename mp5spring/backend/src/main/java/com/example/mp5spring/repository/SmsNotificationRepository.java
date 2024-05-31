package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.SmsNotification;

public interface SmsNotificationRepository extends CrudRepository<SmsNotification, Long> {
}
