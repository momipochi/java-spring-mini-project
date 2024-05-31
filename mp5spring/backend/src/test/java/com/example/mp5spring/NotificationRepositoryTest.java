package com.example.mp5spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.mp5spring.model.Address;
import com.example.mp5spring.model.EmailNotification;
import com.example.mp5spring.model.Notification;
import com.example.mp5spring.model.SmsNotification;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.repository.AddressRepository;
import com.example.mp5spring.repository.EmailNotificationRepository;
import com.example.mp5spring.repository.NotificationRepository;
import com.example.mp5spring.repository.SmsNotificationRepository;
import com.example.mp5spring.repository.UserAccountRepository;

@DataJpaTest
public class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SmsNotificationRepository smsNotificationRepository;
    @Autowired
    private EmailNotificationRepository emailNotificationRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    HibernateValidatorFactory hibernateValidatorFactory;
    Validator validator;

    Notification n1;
    EmailNotification e1;
    SmsNotification s1;

    Address ad1;
    UserAccount u1;

    @BeforeEach
    public void initData() {
        hibernateValidatorFactory = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .unwrap(HibernateValidatorFactory.class);
        validator = hibernateValidatorFactory
                .usingContext()
                .getValidator();
        ad1 = Address.builder()
                .streetName("streetName")
                .zipCode("zipCode")
                .build();
        u1 = UserAccount.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .dateOfBirth(LocalDateTime.now())
                .livesAt(ad1)
                .sex("F")
                .pesel("12345678909")
                .build();
        n1 = Notification.builder()
                .notificationReceiver(u1)
                .build();
        e1 = EmailNotification.builder()
                .fromEmail("bal@gmail.com")
                .toEmail("asds@gmail.com")
                .subject("waddup")
                .body("body")
                .build();
        s1 = SmsNotification.builder()
                .fromPhoneNumber("123456789")
                .toPhoneNumber("987654321")
                .body("body")
                .build();
    }

    // standard check for dependencies
    @Test
    public void testRequiredDependencies() {
        assertNotNull(notificationRepository);
        assertNotNull(smsNotificationRepository);
        assertNotNull(emailNotificationRepository);
        assertNotNull(userAccountRepository);
        assertNotNull(addressRepository);
    }

    // check if notification is received by correct userAccount
    @Test
    public void testSaveNotificationCheckReceiver(){
        n1.setEmailNotification(e1);
        Set<Notification> nots = new HashSet<>();
        nots.add(n1);
        u1.setNotifications(nots);
        addressRepository.save(ad1);
        userAccountRepository.save(u1);
        
        notificationRepository.save(n1);
        var not = notificationRepository.findById(n1.getId());
        assertEquals(u1, not.get().getNotificationReceiver());
        var usrNot = userAccountRepository.findById(u1.getId());
        
        assertEquals(usrNot.get().getNotifications().iterator().next(),not.get());
    }


    // test notification custom OR constraint
    @Test
    public void testNotificationOrConstraint() {

        Set<ConstraintViolation<Notification>> constraintViolations = validator.validate(n1);
        assertEquals(1, constraintViolations.size());

        n1.setEmailNotification(e1);
        constraintViolations = validator.validate(n1);
        assertEquals(0, constraintViolations.size());

        n1.setSmsNotification(s1);
        constraintViolations = validator.validate(n1);
        assertEquals(0, constraintViolations.size());
    }

    // test if both emails and sms are saved to notification
    @Test
    public void testSaveEmailSms() {
        addressRepository.save(ad1);
        userAccountRepository.save(u1);
        n1.setSmsNotification(s1);
        smsNotificationRepository.save(s1);
        n1.setEmailNotification(e1);
        emailNotificationRepository.save(e1);
        notificationRepository.save(n1);
        entityManager.flush();

        var n = notificationRepository.findById(n1.getId());
        assertEquals(n.get().getEmailNotification(), e1);
        assertEquals(n.get().getSmsNotification(), s1);
    }

    // test if email saved properly
    @Test
    public void testSaveEMail() {
        addressRepository.save(ad1);
        userAccountRepository.save(u1);
        n1.setEmailNotification(e1);
        emailNotificationRepository.save(e1);
        notificationRepository.save(n1);
        entityManager.flush();
        var n = notificationRepository.findById(n1.getId());
        assertEquals(n.get().getEmailNotification(), e1);
    }

    // test if sms saved properly
    @Test
    public void testSaveSms() {
        addressRepository.save(ad1);
        userAccountRepository.save(u1);
        n1.setSmsNotification(s1);
        smsNotificationRepository.save(s1);
        notificationRepository.save(n1);
        entityManager.flush();
        var n = notificationRepository.findById(n1.getId());
        assertEquals(n.get().getSmsNotification(), s1);
    }
}
