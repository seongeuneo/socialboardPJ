package com.se.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.se.social.entity.Board;
import com.se.social.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository repository;
	
	// selectList
	public List<Board> selectList() {
		return repository.findAll();
	}
	
	// selectDetail
	public Board selectDetail(int board_id) {
		Optional<Board> result = repository.findById(board_id);
		if (result.isPresent())
			return result.get();
		else
			return null;
	}
	
	// insert, update
	public String save(Board entity) {
		repository.save(entity); 
		return entity.getBoard_id(); 
	}

	// delete
	public int delete(int board_id) {
		repository.deleteById(board_id);
		return board_id; 
	}


  
}