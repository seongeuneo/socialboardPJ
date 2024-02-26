package com.se.social.service;

import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Comments;

public interface CommentsService {
	// selectList
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id);

	// selectDetail
	public Comments selectDetail(int comment_id);

	// insert, update
	public int save(Comments entity);

	// delete
	public int delete(int comment_id);

}
