package com.se.social.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.social.domain.LikesId;
import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Board;
import com.se.social.entity.Comments;
import com.se.social.entity.Likes;
import com.se.social.entity.User;
import com.se.social.repository.BoardRepository;
import com.se.social.repository.LikesRepository;
import com.se.social.service.BoardService;
import com.se.social.service.CommentsService;
import com.se.social.service.LikesService;
import com.se.social.service.NoticeReply;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping(value = "/board")
public class BoardController {

	private BoardService boardService;
	private final LikesService likesService;
	private final CommentsService commentsService;
	private final BoardRepository boardRepository;

	// List =====================================================
	@GetMapping("/boardPage")
	public void getBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchType", defaultValue = "") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(5).build();
		PageResultDTO<Board> resultDTO = boardService.selectList(requestDTO, searchType, keyword);

		model.addAttribute("selectList", resultDTO.getEntityList());
		model.addAttribute("resultDTO", resultDTO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);

	}

	// 새글 작성 페이지 이동
	@GetMapping("/boardInsert")
	public void getBoardInsert() {
	};

	// 새글 작성
	@PostMapping("/boardInsert")
	public String postBoardInsert(RedirectAttributes rttr, HttpServletRequest request, Board entity, Model model)
			throws IOException {
		String uri = "redirect:boardPage";

		entity.setBoard_delyn('N');

		try {
			// 2. Service 처리
			entity.setBoard_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			int savedId = boardService.save(entity);

			if (savedId > 0) {
				rttr.addFlashAttribute("message", "게시글 등록 완료.");

			} else {
				rttr.addFlashAttribute("message", "게시글등록 실패. 다시 하세요.");
				uri = "board/boardInsert";
			}
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("message", "게시글등록 실패. 다시 하세요.");
			uri = "board/boardInsert";
		}

		// 3. View
		return uri;
	}

	// 게시글 상세 이동 (+댓글리스트 까지)
	@GetMapping("/boardDetail")
	public String getBoardDetail(@RequestParam("board_id") int board_id, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		// 조회수
		  // board_id가 같은 경우에만 조회수 증가
	    if (!model.containsAttribute("boardDetail")) {
	        Board boardDetail = boardService.selectDetail(board_id);
	        model.addAttribute("boardDetail", boardDetail);

	        int currentViews = boardDetail.getBoard_views();
	        boardDetail.setBoard_views(currentViews + 1);
	        boardService.save(boardDetail);
	    }


		// 댓글
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(5).build();
		PageResultDTO<Comments> resultDTO = commentsService.selectList(requestDTO, board_id);

		model.addAttribute("commentsList", resultDTO.getEntityList());
		model.addAttribute("resultDTO", resultDTO);

		return "board/boardDetail";
	};

	// 게시글 수정 이동
	@GetMapping("/boardModify")
	public String getBoardModify(@RequestParam("board_id") int board_id, Model model) {
		Board boardModify = boardService.selectDetail(board_id);
		model.addAttribute("boardModify", boardModify);
		return "board/boardModify";
	};

	// 게시글 수정
	@PostMapping(value = "/updateBoard")
	public String updateBoard(HttpSession session, @ModelAttribute Board updateBoard, Model model) throws IOException {
		model.addAttribute("boardDetail", updateBoard);
		System.out.println("업데이트게시판" + updateBoard);
		String uri = "board/boardDetail";
		updateBoard.setBoard_delyn('N');

		try {
			if (boardService.save(updateBoard) > 0) {
				updateBoard.setBoard_moddate(
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

				boardService.save(updateBoard); // 변경사항 저장
				model.addAttribute("message", "Success");
				return uri;
			} else {
				model.addAttribute("message", "Fail");
				return "redirect:/board/boardModify?board_id=" + updateBoard.getBoard_id();
			}

		} catch (Exception e) {
			model.addAttribute("error", "게시글 업데이트에 실패했습니다.");
			return "redirect:/board/boardModify?board_id=" + updateBoard.getBoard_id();
		}
	}

	// 게시글 삭제
	@PostMapping(value = "/deleteBoard/{board_id}")
	public ResponseEntity<?> deleteBoard(@PathVariable("board_id") int board_id) {
		Board entity = boardService.selectDetail(board_id);

		if (entity != null) {
			entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setBoard_delyn('Y');
			boardService.save(entity);
		}

		return ResponseEntity.ok().build();
	}

	// 좋아요
	@PostMapping("/toggle")
	@ResponseBody
	public ResponseEntity<?> toggleLike(@RequestBody Likes entity) {
		try {
			LikesId id = new LikesId(entity.getUseremail(), entity.getBoard_id());
			Board boardEntity = boardService.selectDetail(entity.getBoard_id());

			if (boardEntity != null) {
				Likes existingLike = likesService.selectDetail(id);

				if (existingLike == null) {
					// 좋아요 추가
					boardEntity.setBoard_likes(boardEntity.getBoard_likes() + 1);
					likesService.save(entity);
					boardService.save(boardEntity); // 트랜잭션 처리

					System.out.println("좋아요 추가 완료. 현재 좋아요 개수: " + boardEntity.getBoard_likes());

					return ResponseEntity.ok().build();
				} else {
					// 좋아요 취소
					boardEntity.setBoard_likes(boardEntity.getBoard_likes() - 1);
					likesService.delete(id);
					boardService.save(boardEntity); // 트랜잭션 처리

					System.out.println("좋아요 취소 완료. 현재 좋아요 개수: " + boardEntity.getBoard_likes());

					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당 게시글을 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	// 댓글 쓰기
	@PostMapping("/postComments")
	public String insertComments(@ModelAttribute Comments data, @ModelAttribute Board board, HttpServletRequest request) {
		
		// 데이터 저장이 성공한 경우 Referer 헤더의 값으로 리다이렉트
		data.setComment_delyn("'N'");
		data.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		data.setBoard_id(data.getBoard_id());

		commentsService.save(data);

		return "redirect:boardDetail?board_id=" + data.getBoard_id();
	}

	// 댓글 수정
	@PostMapping("/updateComments")
	@ResponseBody
	public String updateComments(@RequestParam("comment_id") int commentId,
			@RequestParam("comment_content") String updatedContent,
			HttpSession session, Model model) {
		try {
			// 댓글 엔티티를 불러와서 수정 메서드 호출
			Comments comment = commentsService.selectDetail(commentId); // findById 메서드는 댓글 ID로 댓글을 찾는 메서드로 가정합니다.
			if (comment != null) {
				comment.setComment_content(updatedContent);
				comment.setComment_moddate(
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				commentsService.save(comment); // 댓글을 저장하는 메서드로 가정합니다.
				return "Success";
			} else {
				return "Fail"; // 댓글을 찾을 수 없는 경우
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "댓글 업데이트에 실패했습니다.");
			return "Error"; // 예외 발생 시
		}
	}
	
	// 댓글 삭제
	@DeleteMapping(value = "/deleteComments")
	@ResponseBody
	public ResponseEntity<?> deleteComment(@RequestParam("comment_id") int commentId) {
		Comments entity = commentsService.selectDetail(commentId);

		if (entity != null) {
			entity.setComment_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setComment_delyn("'Y'");
			commentsService.save(entity);
		}

		return ResponseEntity.ok().build();
	}
	
	// 대댓글
	@PostMapping(value="/rinsert")
	public String rInsert(Comments comment, HttpSession session) {
		int m_num = (Integer) session.getAttribute("m_num");
		int number = commentsService.maxNum(); // 새 댓글 번호 생성, ref는 nor_num이랑 똑같음

		// ref 답변글끼리 뭉칠때, re_step 답변글 순서, re_level 들여쓰기
		int nor_re_step = 0, nor_re_level = 0; // 첫번째 댓글은 0,0 기본 세팅
		int no_num = comment.getBoard_id(); // 어떤 글에 댓글 썼는지 번호 가져오기(jsp에서 보냈음)
		int nor_num = comment.getComment_id(); // 어떤 댓글에 댓글 남긴건지(jsp에서 보냈음)
		String nor_content = comment.getComment_content(); // 댓글내용(jsp에서 보냈음)

		if (nor_num != 0) { // 댓글에 댓글을 달 때
			NoticeReply comment1 = commentsService.select(nor_num); // 읽어온 댓글의 re_step과 re_level을 알기 위해서
			if (comment1.getNor_re_step() == 0 && comment1.getNor_re_level() == 0) {
				comment.setNor_ref(nor_num); // 대댓글끼리 뭉치기위해, 부모댓글의 댓글번호로 ref 세팅
				int maxStep = comments.maxStep(comment1.getNor_ref()); // 댓글중에서 새로운 댓글 달때 맨 밑으로 가기 위해서
				comment.setNor_re_step(maxStep);
				comment.setNor_re_level(comment1.getNor_re_level() + 1);
			} else { // 댓글의 대댓글을 달 때
				comment.setNor_ref(comment1.getNor_ref()); // 대댓글끼리 뭉치기위해, 부모댓글의 댓글번호로 ref 세팅
				comment.setNor_re_step(comment1.getNor_re_step());
				// 새로운 댓글은 사이에 껴야되기 때문에
				commentsService.updateStep(comment); // 글을 읽고 ref가 같고 re_step이 읽은 글의 re_step보다 크면 그글의 re_step + 1

				comment.setNor_ref(comment1.getNor_ref());
				comment.setNor_re_step(comment1.getNor_re_step() + 1); // 댓글(읽은값)단 re_step보다 1 증가
				comment.setNor_re_level(comment1.getNor_re_level() + 1); // 댓글(읽은값)단 re_level보다 1 증가
			}
		} else {
			comment.setNor_ref(number);
			comment.setNor_re_step(nor_re_step); // 기본 댓글에는 0세팅
			comment.setNor_re_level(nor_re_level); // "
		}

		comment.setNor_content(nor_content);
		comment.setNor_num(number);
		comment.setNo_num(no_num);
		comment.setM_num(m_num);

		commentsService.insert(comment);

		// 결과를 jsp로 보내지 않고 controller내에서 찾을 때 : redirect 또는 forward
		return "redirect:/notice/noticeReplyList.do?no_num=" + comment.getNo_num();
	}
	

}
