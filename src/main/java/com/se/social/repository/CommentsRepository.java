package com.se.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.social.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
