package com.example.mp5spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.mp5spring.model.Address;
import com.example.mp5spring.model.Employee;
import com.example.mp5spring.model.Person;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.repository.AddressRepository;
import com.example.mp5spring.repository.EmployeeRepository;
import com.example.mp5spring.repository.PersonRepository;
import com.example.mp5spring.repository.UserAccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {
        @Autowired
        private EmployeeRepository employeeRepository;
        @Autowired
        private UserAccountRepository userAccountRepository;
        @Autowired
        private PersonRepository personRepository;
        @Autowired
        private AddressRepository addressRepository;

        @PersistenceContext
        private EntityManager entityManager;

        UserAccount u1, u2;
        Employee e1, e2;
        Address ad1;

        @BeforeEach
        public void initData() {
                ad1 = Address.builder()
                                .streetName("streetName")
                                .zipCode("zipCode")
                                .build();
                u1 = UserAccount.builder()
                                .username("username")
                                .firstName("firstName")
                                .lastName("lastName")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad1)
                                .build();
                u2 = UserAccount.builder()
                                .username("username2")
                                .firstName("firstName2")
                                .lastName("lastName2")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad1)
                                .build();
                e1 = Employee.builder()
                                .firstName("firstName")
                                .lastName("lastName")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad1)
                                .build();
                e2 = Employee.builder()
                                .firstName("firstName2")
                                .lastName("lastName2")
                                .dateOfBirth(LocalDateTime.now())
                                .pesel("12345678909").sex("F")
                                .livesAt(ad1)
                                .bonus(2)
                                .build();
        }

        // standard check for dependencies
        @Test
        public void testRequiredDependencies() {
                assertNotNull(employeeRepository);
                assertNotNull(userAccountRepository);
                assertNotNull(personRepository);
        }
        
        // test fetching of person
        @Test
        public void testFetchPerson() {
                employeeRepository.saveAll(Arrays.asList(e1, e2));
                userAccountRepository.saveAll(Arrays.asList(u1, u2));
                entityManager.flush();
                Iterable<Person> people = personRepository.findAll();
                int count = 0;
                for (Person ppl : people) {
                        count++;
                        System.out.println(ppl);
                }
                assertEquals(4, count);
        }

        // test saving all people
        @Test
        public void testSaveAll() {
                employeeRepository.saveAll(Arrays.asList(e1, e2));
                userAccountRepository.saveAll(Arrays.asList(u1, u2));
                entityManager.flush();
                assertEquals(2L, employeeRepository.count());
                assertEquals(2L, userAccountRepository.count());
        }
}
