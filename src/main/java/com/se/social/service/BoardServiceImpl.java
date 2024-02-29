package com.se.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Board;
import com.se.social.entity.Comments;
import com.se.social.entity.QBoard;
import com.se.social.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.se.social.entity.QBoard.board;;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final QBoard board = QBoard.board;
	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;

	@Override
	public PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword) {
		BooleanExpression searchCondition = getSearchCondition(searchType, keyword);

		// QueryResults 자바에서 지원해주는 것
		QueryResults<Board> result = queryFactory.selectFrom(board)
				.where(board.board_delyn.eq('N').and(searchCondition)).orderBy(board.board_id.desc())
				.offset(requestDTO.getPageable().getOffset()).limit(requestDTO.getPageable().getPageSize())
				.fetchResults();
		// count의 역할
		// 요즘은 지원안하는 함수

		return new PageResultDTO<>(result, requestDTO.getPageable());
	}

	private BooleanExpression getSearchCondition(String searchType, String keyword) {
		if ("all".equals(searchType) || "".equals(keyword)) {
			return null;
		}

		switch (searchType) {
		case "useremail":
			return board.useremail.contains(keyword);
		case "board_title":
			return board.board_title.contains(keyword);
		case "board_content":
			return board.board_content.contains(keyword);
		default:
			return null;
		}
	}

	//

	// selectDetail
	@Override
	public Board selectDetail(int board_id) {
		Optional<Board> result = repository.findById(board_id);
		if (result.isPresent())
			return result.get();
		else
			return null;
	}

	// insert, update
	@Override
	public int save(Board entity) {
		repository.save(entity);
		return entity.getBoard_id();
	}
	


	// delete
	@Override
	public int delete(int board_id) {
		repository.deleteById(board_id);
		return board_id;
	}
	
}