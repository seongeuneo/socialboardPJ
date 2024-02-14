package com.se.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se.social.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}
