package com.se.social.service;

import com.se.social.domain.LikesId;
import com.se.social.entity.Likes;

public interface LikesService {
	// selectDetail
	public Likes selectDetail(LikesId likesId);

	// insert, update
	public int save(Likes entity);

	// delete
	public LikesId delete(LikesId likesId);


}