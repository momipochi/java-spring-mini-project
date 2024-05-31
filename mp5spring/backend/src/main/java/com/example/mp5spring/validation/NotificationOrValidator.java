package com.example.mp5spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.mp5spring.model.Notification;

public class NotificationOrValidator implements ConstraintValidator<NotificationOrConstraint, Notification> {
    @Override
    public void initialize(NotificationOrConstraint subset) {
    }

    @Override
    public boolean isValid(Notification notification, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        System.out.println("Validating notification....");
        if (notification == null) {
            return false;
        }
        return notification.getEmailNotification() != null || notification.getSmsNotification() != null;
    }

}
