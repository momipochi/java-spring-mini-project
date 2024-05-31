package com.example.mp5spring.repository;

import com.example.mp5spring.model.Comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
