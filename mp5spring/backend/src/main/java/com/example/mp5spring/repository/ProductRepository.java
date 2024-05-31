package com.example.mp5spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mp5spring.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
