package com.example.mp5spring;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.mp5spring.model.Address;
import com.example.mp5spring.model.Channel;
import com.example.mp5spring.model.Comment;
import com.example.mp5spring.model.Department;
import com.example.mp5spring.model.EmailNotification;
import com.example.mp5spring.model.Employee;
import com.example.mp5spring.model.Notification;
import com.example.mp5spring.model.Playlist;
import com.example.mp5spring.model.SmsNotification;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.model.Video;
import com.example.mp5spring.repository.AddressRepository;
import com.example.mp5spring.repository.ChannelRepository;
import com.example.mp5spring.repository.CommentRepository;
import com.example.mp5spring.repository.DepartmentRepository;
import com.example.mp5spring.repository.EmailNotificationRepository;
import com.example.mp5spring.repository.EmployeeRepository;
import com.example.mp5spring.repository.NotificationRepository;
import com.example.mp5spring.repository.PlaylistRepository;
import com.example.mp5spring.repository.ProductRepository;
import com.example.mp5spring.repository.SmsNotificationRepository;
import com.example.mp5spring.repository.UserAccountRepository;
import com.example.mp5spring.repository.VideoRepository;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

        // @EventListener
        // public void initDb(ContextRefreshedEvent ev){

        // }
        @Autowired
        private DepartmentRepository departmentRepository;
        @Autowired
        private EmployeeRepository employeeRepository;
        @Autowired
        private ChannelRepository channelRepository;
        @Autowired
        private UserAccountRepository userAccountRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private VideoRepository videoRepository;
        @Autowired
        private CommentRepository commentRepository;
        @Autowired
        private PlaylistRepository playlistRepository;
        @Autowired
        private NotificationRepository notificationRepository;
        @Autowired
        private SmsNotificationRepository smsNotificationRepository;
        @Autowired
        private EmailNotificationRepository emailNotificationRepository;
        @Autowired
        private AddressRepository addressRepository;

        @PersistenceContext
        private EntityManager entityManager;
        Department d1;
        Employee e1;
        Channel c1, c2;
        UserAccount u1, u2;
        Video v1, v2, v3, v4,v5;
        Comment cmt1;
        Playlist p1, p2;
        Address ad1, ad2, ad3;
        Notification n1;
        EmailNotification em1;
        SmsNotification s1;

        @Transactional
        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
                // TODO Auto-generated method stub
                initData();
                seedDatabase();
        }

        @Transactional
        private void seedDatabase() {
                
                n1.setEmailNotification(em1);
                em1.setNotificationEmail(n1);
                Set<Notification> nots = new HashSet<>();
                nots.add(n1);
                u1.setNotifications(nots);

                
                addressRepository.saveAll(Arrays.asList(ad1, ad2, ad3));
                u1.setChannel(c1);
                userAccountRepository.saveAll(Arrays.asList(u1, u2));
                emailNotificationRepository.save(em1);
                notificationRepository.save(n1);
                channelRepository.saveAll(Arrays.asList(c1,c2));
                videoRepository.saveAll(Arrays.asList(v1, v2, v3,v4,v5));
                playlistRepository.saveAll(Arrays.asList(p1, p2));
                commentRepository.save(cmt1);
                departmentRepository.save(d1);
                employeeRepository.save(e1);
                entityManager.flush();
        }

        @Transactional
        private void initData() {
                d1 = Department.builder()
                                .departmentName("Marketing")
                                .build();
                ad1 = Address.builder()
                                .streetName("Aleja Bla Bla")
                                .zipCode("01-233")
                                .build();
                ad2 = Address.builder()
                                .streetName("Washington Street")
                                .zipCode("11-111")
                                .build();
                ad3 = Address.builder()
                                .streetName("Funk Town")
                                .zipCode("69-420")
                                .build();

                e1 = Employee.builder()
                                .livesAt(ad1)
                                .firstName("John")
                                .lastName("China")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .build();
                u1 = UserAccount.builder()
                                .livesAt(ad2)
                                .username("smithWillSmithSmith")
                                .firstName("Will")
                                .lastName("Myth")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .build();
                n1 = Notification.builder()
                                .notificationReceiver(u1)
                                .build();
                em1 = EmailNotification.builder()
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
                u2 = UserAccount.builder()
                                .livesAt(ad3)
                                .username("not_the_real_spiderman")
                                .firstName("Pedo")
                                .lastName("Parker")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("42069696969").sex("F")
                                .build();
                c1 = Channel.builder()
                                .channelName("McDonald")
                                .ownerOfChannel(u1)
                                .build();
                c2 = Channel.builder()
                                .channelName("Meme center")
                                .ownerOfChannel(u2)
                                // .ownedProductsPrivate(new HashSet<>(Arrays.asList(v1)))
                                .build();
                v1 = Video.builder()
                                .productName("How to become a billionaire 101 MUST WATCH!!")
                                .description("Very cool description very cool")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                v2 = Video.builder()
                                .productName("Tutorial - Downloading rams")
                                .description("Watch how to download rams on to your computer to boost your computer's power")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                v3 = Video.builder()
                                .productName("Chug jug with me")
                                .description("Fortnite 49 chug jug with me")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                v4 = Video.builder()
                                .productName("Top ten n-words")
                                .description("Today we're going to be looking at my favorite n-words")
                                .ownedByChannel(c2)
                                .durationInMs(12345)
                                .build();
                v5 = Video.builder()
                                .productName("Arnold Schwarzenegger get to the chopper 10 hours")
                                .description("GET TO DA CHOPPA NAU")
                                .ownedByChannel(c2)
                                .durationInMs(12345)
                                .build();
                cmt1 = Comment.builder()
                                .comment("I hate your video please log off of ethernet")
                                .commenter(u1)
                                .productCommentedOn(v1)
                                .timeOfComment(LocalDateTime.now())
                                .build();
                p1 = Playlist.builder()
                                .savedBy(Arrays.asList(c1))
                                .savedProducts(Arrays.asList(v1, v2, v3, v3, v3))
                                .playlistName("Epic playlist best montage")
                                .build();
                p2 = Playlist.builder()
                                .savedBy(Arrays.asList(c1))
                                .savedProducts(Arrays.asList(v3,v1, v2, v4,v5))
                                .playlistName("This is my prviate playlist STOP CLICKING IT")
                                .build();
        }
}
