package com.se.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.se.social.entity.Comments;
import com.se.social.repository.BoardRepository;
import com.se.social.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService{
	
	private final CommentsRepository repository;
	
	// selectList
	public List<Comments> selectList() {
		return repository.findAll();
	}
	
	// selectDetail
	public Comments selectDetail(int comment_id) {
		Optional<Comments> result = repository.findById(comment_id);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	// insert, update
	public int save(Comments entity) {
		repository.save(entity);
		return entity.getComment_id();
	}
	
	// delete
	public int delete(int comment_id) {
		repository.deleteById(comment_id);
		return comment_id;
		
	}
	
  
}