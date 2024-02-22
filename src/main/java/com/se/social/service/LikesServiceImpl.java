package com.se.social.service;

import org.springframework.stereotype.Service;

import com.se.social.domain.LikesId;
import com.se.social.entity.Likes;
import com.se.social.repository.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

	private final LikesRepository repository;

	// selectDetail
	@Override
	public Likes selectDetail(LikesId likesId) {
        return repository.findById(likesId).orElse(null);
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