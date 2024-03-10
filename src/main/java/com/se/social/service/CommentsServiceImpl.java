package com.se.social.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.dml.UpdateClause;
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
    public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id){
       QueryResults<Comments> result = queryFactory
                .selectFrom(comments)
                .where(comments.comment_delyn.eq("'N'").and(comments.board_id.eq(id)))
                .orderBy(comments.comment_root.asc(),comments.comment_steps.asc())
                .offset(requestDTO.getPageable().getOffset())
                .limit(requestDTO.getPageable().getPageSize())
                .fetchResults();
       return new PageResultDTO<>(result, requestDTO.getPageable());
    }

	// selectDetail
	@Override
	public Comments selectDetail(int comment_id) {
		Optional<Comments> result = repository.findById(comment_id);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	// insert, update
	@Override
	public int save(Comments entity) {
		log.info("댓글 수정 시작, comment_id: {}", entity.getComment_id());

		// 로깅 추가
		log.info("댓글 수정 - comment_content: {}", entity.getComment_content());

		repository.save(entity);
		return entity.getComment_id();
	}

	// step을 위한 메서드
	@Override
    @Transactional
    public void stepUpdate(Comments entity) {
       UpdateClause update = queryFactory.update(comments)
              .set(comments.comment_steps, comments.comment_steps.add(1))
              .where(comments.comment_root.eq(entity.getComment_root())
              .and(comments.comment_steps.goe(entity.getComment_steps()))
              .and(comments.comment_id.ne(entity.getComment_id()))
            );
         update.execute();
    }
	

	// delete
	@Override
	public int delete(int comment_id) {
		repository.deleteById(comment_id);
		return comment_id;

	}

}