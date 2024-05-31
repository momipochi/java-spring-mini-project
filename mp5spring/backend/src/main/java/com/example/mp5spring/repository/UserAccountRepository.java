package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    
}
