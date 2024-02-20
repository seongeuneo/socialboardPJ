package com.se.social.service;

import java.util.List;
import java.util.Optional;

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
	
	// 삭제 기능 (board_delyn 상태 변화시키기)
	public void updateBoardDelyn(int board_id);
	
	// 좋아요 추가하기
	void incrementLikes(int boardId);
	int getLikes(int boardId);

	// delete
	public int delete(int board_id);
}
