package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
