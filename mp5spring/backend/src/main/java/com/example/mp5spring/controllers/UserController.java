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

import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.repository.UserAccountRepository;

@CrossOrigin(origins = Constants.baseUrl)
@RestController
@RequestMapping(Constants.baseMapping)
public class UserController {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/getUserAccounts")
    public ResponseEntity<List<UserAccount>> getUserAccounts() {
        try {
            List<UserAccount> userAccounts = new ArrayList<>();
            userAccountRepository.findAll().forEach(userAccounts::add);
            System.out.println(userAccounts.size());
            // return new ResponseEntity<>(null, HttpStatus.OK);
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/newUserAccount")
    public ResponseEntity<UserAccount> createNewUserAccount(@RequestBody UserAccount userAccount) {
        try {
            if (userAccount == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            if (!userAccountRepository.findById(userAccount.getId()).isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            UserAccount usr = userAccountRepository.save(userAccount);

            return new ResponseEntity<>(usr, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
