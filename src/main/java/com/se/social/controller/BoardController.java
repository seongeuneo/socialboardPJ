package com.se.social.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.social.domain.LikesId;
import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Board;
import com.se.social.entity.Comments;
import com.se.social.entity.Likes;
import com.se.social.repository.LikesRepository;
import com.se.social.service.BoardService;
import com.se.social.service.CommentsService;
import com.se.social.service.LikesService;

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

	// 게시글 상세 이동
	@GetMapping("/boardDetail")
	public String getBoardDetail(@RequestParam("board_id") int board_id, Model model) {
		Board boardDetail = boardService.selectDetail(board_id);
		model.addAttribute("boardDetail", boardDetail);

		// 조회수
		int currentViews = boardDetail.getBoard_views();
		boardDetail.setBoard_views(currentViews + 1);
		boardService.save(boardDetail);
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
	public String insertComments(@ModelAttribute Comments data, HttpServletRequest request) {

		// 데이터 저장이 성공한 경우 Referer 헤더의 값으로 리다이렉트
		data.setComment_delyn("'N'");
		data.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		commentsService.save(data);
		
		return "redirect:boardDetail?board_id=" + data.getBoard_id();
	}
	
	// 댓글 가져오기
	@GetMapping("/commentsDetail")
	public void getComments(@RequestParam("comment_id") int comment_id, Model model) {
	    Comments commentsDetail = commentsService.selectDetail(comment_id);
	    model.addAttribute("commentsDetail", commentsDetail);
	}


}
