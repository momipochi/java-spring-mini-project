package com.example.mp5spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.mp5spring.model.Address;
import com.example.mp5spring.repository.AddressRepository;

@DataJpaTest
class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Address ad1;

    @BeforeEach
    public void initData() {
        ad1 = Address.builder().streetName("hyyhk").zipCode("kjygj").build();
    }

    //check if repository loaded
    @Test
    public void testRequiredDependencies() {
        assertNotNull(addressRepository);
    }

    //check if repository works
    @Test
    public void testFetchAddresses() {
        Iterable<Address> addresses = addressRepository.findAll();
        for (Address addr : addresses) {
            System.out.println(addr);
        }
    }

    //check if repository saved entity properly
    @Test
    public void testSaveAddress() {
        addressRepository.save(ad1);
        long count = addressRepository.count();
        assertEquals(1, count);
    }

    //test if constraints work
    @Test
    public void testSaveInvalidStreetName() {
        assertThrows(ConstraintViolationException.class, () -> {
        ad1.setStreetName("s");
        addressRepository.save(ad1);
        entityManager.flush();
        });
    }

    //test findBy with repo
    @Test
    public void testFindByName() {
        var addresses = addressRepository.findByStreetName("sss");
        System.out.println(addresses);
        assertEquals(0, addresses.size());
    }

    //test address query with repo
    @Test
    public void testFindIdBelow() {
        addressRepository.save(ad1);
        entityManager.flush();
        var res = addressRepository.findAddressesIdLowerThan(990L);
        System.out.println(res);
        assertEquals(1, res.size());
    }
}