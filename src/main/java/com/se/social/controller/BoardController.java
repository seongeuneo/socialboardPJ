package com.se.social.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.se.social.domain.PageRequestDTO;
import com.se.social.domain.PageResultDTO;
import com.se.social.entity.Board;
import com.se.social.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping(value = "/board")
public class BoardController {

	private BoardService boardService;

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
		Board entity = 	boardService.selectDetail(board_id);
		
		if(entity != null) {
			entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setBoard_delyn('Y');
			boardService.save(entity);
		}

		return ResponseEntity.ok().build();
	}
	
	// 좋아요 기능
	@PostMapping("/likeBoard")
	@ResponseBody
	public ResponseEntity<?> likeBoard(@RequestParam("board_id") int boardId) {
	    boardService.incrementLikes(boardId);
	    int updatedLikes = boardService.getLikes(boardId);
	    return ResponseEntity.ok(Map.of("likes", updatedLikes));
	}


}
