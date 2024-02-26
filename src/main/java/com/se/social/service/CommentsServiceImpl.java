package com.se.social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Comments;
import com.se.social.entity.QComments;
import com.se.social.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

	private final CommentsRepository repository;
	private final JPAQueryFactory queryFactory;
	private final QComments comments = QComments.comments;

	// selectList
	@Override
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int board_id) {
		QueryResults<Comments> result = queryFactory.selectFrom(comments)
				.where(comments.comment_delyn.eq("'N'").and(comments.board_id.eq(board_id)))
				.offset(requestDTO.getPageable().getOffset()).limit(requestDTO.getPageable().getPageSize())
				.fetchResults();

		return new PageResultDTO<>(result, requestDTO.getPageable());
	}

	// selectDetail
	public Comments selectDetail(int comment_id) {
		Optional<Comments> result = repository.findById(comment_id);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	// insert, update
	public int save(Comments entity) {
		log.info("댓글 수정 시작, comment_id: {}", entity.getComment_id());

		// 로깅 추가
		log.info("댓글 수정 - comment_content: {}", entity.getComment_content());

		repository.save(entity);
		return entity.getComment_id();
	}

	// update
	@Override
	public int update(Comments entity) {
		log.info("댓글 수정 시작, comment_id: {}", entity.getComment_id());
		Optional<Comments> existingComment = repository.findById(entity.getComment_id());

		if (existingComment.isPresent()) {
			Comments commentToUpdate = existingComment.get();

			// 여기에서 수정할 필드들을 업데이트합니다.
			commentToUpdate.setComment_content(entity.getComment_content());

			// 그 외 필요한 필드들도 업데이트할 수 있습니다.

			repository.save(commentToUpdate); // JpaRepository의 save 메소드를 사용하여 업데이트
			return commentToUpdate.getComment_id();
		} else {
			log.error("댓글을 찾을 수 없습니다. comment_id: {}", entity.getComment_id());
			return -1; // 예외 처리 또는 에러 처리를 하세요.
		}
	}

	// delete
	public int delete(int comment_id) {
		repository.deleteById(comment_id);
		return comment_id;

	}

}