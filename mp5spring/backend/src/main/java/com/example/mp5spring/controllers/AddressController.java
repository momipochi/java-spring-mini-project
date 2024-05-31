package com.example.mp5spring.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mp5spring.model.Address;
import com.example.mp5spring.repository.AddressRepository;

@CrossOrigin(origins = Constants.baseUrl)
@RestController
@RequestMapping(Constants.baseMapping)
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/testing")
    public ResponseEntity<String> testing() {
        return new ResponseEntity<>("Its working", HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {
            List<Address> addresses = new ArrayList<>();
            addressRepository.findAll().forEach(addresses::add);
            if (addresses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> createAddresse(@RequestBody Address address) {
        try {

            if (address == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            //probably no need to check id, consider getting rid
            if (!addressRepository.findById(address.getId()).isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            Address add = addressRepository.save(address);

            return new ResponseEntity<>(add, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
