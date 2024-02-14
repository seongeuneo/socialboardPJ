package com.se.social.service;

import java.util.List;
import java.util.Optional;

import com.se.social.entity.Comments;

public interface CommentsService {
	// selectList
	public List<Comments> selectList();

	// selectDetail
	public Comments selectDetail(int comment_id);

	// insert, update
	public int save(Comments entity);

	// delete
	public int delete(int comment_id);

}
