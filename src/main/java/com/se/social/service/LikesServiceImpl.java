package com.se.social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.se.social.domain.LikesId;
import com.se.social.entity.Board;
import com.se.social.entity.Likes;
import com.se.social.repository.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

	private final LikesRepository repository;

	// selectDetail
	@Override
	public Optional<Board> selectDetail(int board_id) {
	    return repository.findById(new LikesId(null, board_id)).map(Likes::getBoard);
	}
	
	// insert, update
	@Override
	public int save(Likes entity) {
		repository.save(entity);
		return entity.getBoard_id();
	}

	// delete
	@Override
	public LikesId delete(LikesId likesId) {
		repository.deleteById(likesId);
		return likesId;

	}
}