package com.example.mp5spring.repository;

import com.example.mp5spring.model.Address;

import org.springframework.data.repository.CrudRepository;

public interface StreamRepository extends CrudRepository<Address, Long> {

}
