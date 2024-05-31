package com.example.mp5spring.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mp5spring.model.Employee;
import com.example.mp5spring.model.UserAccount;
import com.example.mp5spring.repository.UserAccountRepository;

public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount smartCopy(Employee subclass, String username) {
        // TODO Auto-generated method stub
        if (subclass == null) {
            try {
                throw new Exception("Smart copy object should not be null");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new UserAccount(subclass, username);
    }

}
