package com.se.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.social.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
