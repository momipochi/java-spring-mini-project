package com.example.mp5spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
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
import com.example.mp5spring.model.Channel;
import com.example.mp5spring.model.Comment;
import com.example.mp5spring.model.Department;
import com.example.mp5spring.model.Employee;
import com.example.mp5spring.model.Playlist;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.model.Video;
import com.example.mp5spring.repository.ChannelRepository;
import com.example.mp5spring.repository.CommentRepository;
import com.example.mp5spring.repository.DepartmentRepository;
import com.example.mp5spring.repository.EmployeeRepository;
import com.example.mp5spring.repository.PlaylistRepository;
import com.example.mp5spring.repository.ProductRepository;
import com.example.mp5spring.repository.UserAccountRepository;
import com.example.mp5spring.repository.VideoRepository;

@DataJpaTest
public class AssociationTest {
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

        @PersistenceContext
        private EntityManager entityManager;

        HibernateValidatorFactory hibernateValidatorFactory;
        Validator validator;

        Department d1;
        Employee e1;
        Channel c1, c2;
        UserAccount u1, u2;
        Video v1, v2, v3;
        Comment cmt1;
        Playlist p1, p2;
        Address ad1, ad2, ad3;

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
                d1 = Department.builder()
                                .departmentName("departmentName")
                                .build();
                ad1 = Address.builder()
                                .streetName("streetName")
                                .zipCode("zipCode")
                                .build();
                ad2 = Address.builder()
                                .streetName("streetName")
                                .zipCode("zipCode")
                                .build();
                ad3 = Address.builder()
                                .streetName("streetName")
                                .zipCode("zipCode")
                                .build();
                e1 = Employee.builder()
                                .firstName("firstName")
                                .lastName("lastName")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad1)
                                .build();

                u1 = UserAccount.builder()
                                .username("username")
                                .firstName("firstName")
                                .lastName("lastName")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad2)
                                .build();
                u2 = UserAccount.builder()
                                .username("username2")
                                .firstName("firstName")
                                .lastName("lastName")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad3)
                                .build();
                c1 = Channel.builder()
                                .channelName("channelName")
                                .ownerOfChannel(u1)
                                .build();
                c2 = Channel.builder()
                                .channelName("channelName")
                                .ownerOfChannel(u2)
                                .build();
                v1 = Video.builder()
                                .productName("productName")
                                .description("description1")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                v2 = Video.builder()
                                .productName("productName")
                                .description("description1")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                v3 = Video.builder()
                                .productName("productName")
                                .description("description1")
                                .ownedByChannel(c1)
                                .durationInMs(12345)
                                .build();
                cmt1 = Comment.builder()
                                .comment("comment")
                                .timeOfComment(LocalDateTime.now())
                                .build();
                p1 = Playlist.builder()
                                .savedBy(Arrays.asList(c1))
                                .savedProducts(Arrays.asList(v1, v2))
                                .playlistName("playlistName")
                                .build();
                p2 = Playlist.builder()
                                .savedBy(Arrays.asList(c1))
                                .savedProducts(Arrays.asList(v3, v2))
                                .playlistName("playlistName2")
                                .build();
        }

        // standard check for dependencies
        @Test
        public void testRequiredDependencies() {
                assertNotNull(departmentRepository);
                assertNotNull(employeeRepository);
                assertNotNull(userAccountRepository);
                assertNotNull(channelRepository);
                assertNotNull(videoRepository);
                assertNotNull(productRepository);
                assertNotNull(commentRepository);
                assertNotNull(playlistRepository);
        }

        // test query for findAllPlaylistSavedByChannel, should return playlist saved by a channel
        @Test
        public void testFetchPlaylistsOfChannel(){
                u1.setChannel(c1);
                userAccountRepository.save(u1);
                channelRepository.save(c1);
                videoRepository.saveAll(Arrays.asList(v1, v2, v3));
                playlistRepository.saveAll(Arrays.asList(p1, p2));
                entityManager.flush();
                var playlists = playlistRepository.findAllPlaylistSavedByChannel(c1.getId());
                assertEquals(2, playlists.size());
        }

        /*
         * invalid Subset, for a channel to have private Products it should belong in
         * all Products first
         */
        @Test
        public void saveInvalidSubsetChannelProduct() {

                userAccountRepository.save(u2);
                c2.setOwnedProductsPrivate(new HashSet<>(Arrays.asList(v1)));

                Set<ConstraintViolation<Channel>> constraintViolations = validator.validate(c2);
                assertEquals(1, constraintViolations.size());
        }

        /*
         * test if playlist is saved properly
         */
        @Test
        public void savePlaylistTest() {

                u1.setChannel(c1);
                userAccountRepository.save(u1);
                channelRepository.save(c1);
                videoRepository.saveAll(Arrays.asList(v1, v2, v3));
                playlistRepository.saveAll(Arrays.asList(p1, p2));

                entityManager.flush();

                var usr = userAccountRepository.findById(u1.getId());
                var cn = channelRepository.findById(c1.getId());
                var plst = playlistRepository.findById(p1.getId());

                assertEquals(1, plst.get().getSavedBy().size());
        }

        /*
         * test whether UserAccounts holds reference to the correct Channel and vice
         * versa
         */
        @Test
        public void testDeleteUserAccount() {
                u1.setChannel(c1);
                userAccountRepository.save(u1);
                channelRepository.save(c1);
                var usr = userAccountRepository.findById(u1.getId());
                var cn = channelRepository.findById(c1.getId());
                assertEquals(usr.get().getChannel(), cn.get());

                userAccountRepository.delete(usr.get());
                usr = userAccountRepository.findById(u1.getId());
                cn = channelRepository.findById(c1.getId());
                assertFalse(usr.isPresent());
                assertFalse(cn.isPresent());
        }

        @Test
        public void checkUserChannelAssociation() {

                u1.setChannel(c1);
                userAccountRepository.save(u1);
                channelRepository.save(c1);

                var usr = userAccountRepository.findById(u1.getId());
                var cn = channelRepository.findById(c1.getId());
                assertEquals(usr.get().getChannel(), cn.get());
        }

        /*
         * test whether Comment saved and whether it has correct reference to
         * UserAccount and Product
         */
        @Test
        public void testSaveComment() {

                userAccountRepository.save(u1);
                videoRepository.save(v1);
                cmt1.setCommenter(u1);
                cmt1.setProductCommentedOn(v1);
                v1.getComments().add(cmt1);
                u1.getComments().add(cmt1);
                commentRepository.save(cmt1);
                userAccountRepository.save(u1);
                videoRepository.save(v1);

                var cmt = commentRepository.findById(cmt1.getId());
                var usr = userAccountRepository.findById(u1.getId());
                var vid = videoRepository.findById(v1.getId());
                assertEquals(cmt.get().getCommenter(), usr.get());
                assertEquals(cmt.get().getProductCommentedOn(), vid.get());
        }

        /*
         * @Moderators are collection of @UserAccount
         */
        @Test
        public void testSaveModeratorAndProduct() {
                u1.getModeratorOf().add(v1);
                userAccountRepository.save(u1);

                v1.getModerators().add(u1);
                videoRepository.save(v1);

                var vid = videoRepository.findById(v1.getId());

                assertEquals(v1, vid.get());
                assertTrue(vid.get().getModerators().contains(u1));
        }

        // test if products saved properly and is related to correct channel
        @Test
        public void testSaveChannelAndProduct() {
                userAccountRepository.save(u1);
                c1.setOwnerOfChannel(u2);
                assertEquals(c1.getOwnerOfChannel(), u2);
                c1.setOwnerOfChannel(u1);
                c1.getOwnedProducts().add(v1);
                channelRepository.save(c1);

                v1.setOwnedByChannel(c1);
                videoRepository.save(v1);

                var vid = videoRepository.findById(v1.getId());
                var chan = channelRepository.findById(c1.getId());

                System.out.println(vid);
                System.out.println(chan);
                assertEquals(v1, vid.get());
                assertEquals(c1, chan.get());
        }

        // test if channel belongs to correct userAccount
        @Test
        public void testSaveChannelAndUserAccount() {
                
                c1.setOwnerOfChannel(u1);
                u1.setChannel(c1);
                userAccountRepository.save(u1);
                channelRepository.save(c1);

                var c = channelRepository.findById(c1.getId());
                assertTrue(c.isPresent());
                System.out.println(c.get().getOwnerOfChannel());
                assertEquals(u1, c.get().getOwnerOfChannel());
        }

        // test if employee works in correct department
        @Test
        public void testSaveEmployeeAndDepartment() {
                d1.getEmployees().add(e1);
                departmentRepository.save(d1);
                e1.setWorksIn(d1);
                employeeRepository.save(e1);

                var dept = departmentRepository.findById(d1.getId());
                assertTrue(dept.isPresent());
                System.out.println(dept.get().getEmployees());
                assertEquals(1, dept.get().getEmployees().size());
        }

}
