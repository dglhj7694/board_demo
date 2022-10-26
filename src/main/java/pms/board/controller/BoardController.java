package pms.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import pms.board.Service.BoardService;
import pms.board.dto.BoardDto;
import pms.board.repository.CustomBoardRepository;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final CustomBoardRepository customBoardRepository;
	private final BoardService boardService;

	@GetMapping("/")
	public String list(String searchVal, Pageable pageable, Model model) {
		Page<BoardDto> results = customBoardRepository.selectBoardList(searchVal, pageable);
		model.addAttribute("list", results);
		model.addAttribute("maxPage", 5);
		model.addAttribute("searchVal", searchVal);
		pageModelPut(results, model);
		return "board/list";
	}

	// page 처리
	private void pageModelPut(Page<BoardDto> results, Model model) {
		model.addAttribute("totalCount", results.getTotalElements());
		model.addAttribute("size", results.getPageable().getPageSize());
		model.addAttribute("number", results.getPageable().getPageNumber());
	}

	@GetMapping("/write")
	public String write(Model model) {
		model.addAttribute("form", new BoardDto());
		return "board/write";
	}

	@PostMapping("/write")
	public String save(BoardDto boardDto) {
		boardService.saveBoard(boardDto);
		return "redirect:/";
	}

	@GetMapping("/update")
	public String update() {
		return "board/update";
	}
}