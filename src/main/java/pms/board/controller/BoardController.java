package pms.board.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import pms.board.Service.BoardService;
import pms.board.dto.BoardDto;
import pms.board.entity.Board;
import pms.board.repository.CustomBoardRepository;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final CustomBoardRepository customBoardRepository;
	private final BoardService boardService;

	/* list
	 * 게시글 목록화면 */
	@GetMapping("/")
	public String list(String searchVal, Pageable pageable, Model model) {
		Page<BoardDto> results = customBoardRepository.selectBoardList(searchVal, pageable);
		model.addAttribute("list", results);
		model.addAttribute("maxPage", 5);
		model.addAttribute("searchVal", searchVal);
		pageModelPut(results, model);
		return "board/list";
	}

	// page 처리 수정해야..
	private void pageModelPut(Page<BoardDto> results, Model model) {
		model.addAttribute("totalCount", results.getTotalElements());	
		model.addAttribute("size", results.getPageable().getPageSize());	
		model.addAttribute("number", results.getPageable().getPageNumber());
	}

	/* write
	 *  게시물 글쓰기 화면 */
    @GetMapping("/write")
    public String write(Model model){
        model.addAttribute("boardDto", new BoardDto());
        return "board/write";
    }

	/* save
	 * 게시물 등록 */
	@PostMapping("/write")
	public String save(@Valid BoardDto boardDto, BindingResult result) {
		//유효성 검사
		if (result.hasErrors()) {	//오류가 있을경우 다시 글쓰기 화면으로
			return "board/write";
		}
		boardService.saveBoard(boardDto);
		return "redirect:/";
	}

	/* update (get)
	 * 게시물 수정 화면 */
	@GetMapping("/update/{boardId}")
	public String update(@PathVariable Long boardId, Model model) {
		Board board = boardService.selectBoardDetail(boardId);
		BoardDto boardDto = new BoardDto();
		boardDto.setId(boardId);
		boardDto.setTitle(board.getTitle());
		boardDto.setContent(board.getContent());
		model.addAttribute("boardDto",boardDto);	//th:object="${boardDto}"
		
		return "board/update";
	}
	
	/*
	 * update (put 수정)
	 *  게시물 수정
	 */
	@PutMapping("/update/{boardId}")
	public String update(@Valid BoardDto boardDto, BindingResult result) {
		//유효성검사
		if (result.hasErrors()) {
			return "board/update";
		}
		
		boardService.saveBoard(boardDto);
		return "redirect:/";
	}
	
}