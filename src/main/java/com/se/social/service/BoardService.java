package com.se.social.service;

import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Board;

public interface BoardService {

	// selectList
	public PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword);

	// selectDetail
	public Board selectDetail(int board_id);

	// insert, update
	public int save(Board entity);
	
	
	// delete
	public int delete(int board_id);
}
