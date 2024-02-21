package com.se.social.service;

import java.util.Optional;

import com.se.social.domain.LikesId;
import com.se.social.entity.Board;
import com.se.social.entity.Likes;

public interface LikesService {
	// selectDetail
	public Optional<Board> selectDetail(int board_id);

	// insert, update
	public int save(Likes entity);

	// delete
	public LikesId delete(LikesId likesId);


}