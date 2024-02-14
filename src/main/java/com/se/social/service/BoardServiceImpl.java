package com.se.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.se.social.entity.Board;
import com.se.social.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static com.se.social.entity.QBoard.board;;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;

	// selectList
	@Override
	public List<Board> selectList() {
		return queryFactory
				.selectFrom(board)
				.where(board.board_delyn.eq('N'))
				.orderBy(board.board_id.desc())
				.fetch();
	}

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
	public String save(Board entity) {
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