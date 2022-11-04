package pms.board.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pms.board.dto.BoardFileDto;
import pms.board.dto.BoardRequestDto;
import pms.board.dto.BoardResponseDto;
import pms.board.entity.Board;
import pms.board.entity.Category;
import pms.board.entity.Member;
import pms.board.repository.BoardRepository;
import pms.board.repository.CustomBoardRepository;
import pms.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final CustomBoardRepository customBoardRepository;
	private final FileService fileService;
	/* saveBoard
	 * 게시물 등록 */
	@Transactional
	public Long saveBoard(BoardRequestDto boardRequestDto) throws Exception {
		List<Member> memberList = memberRepository.findAll();
		Member member = memberList.get(0);	// 추후 로그인된 멤버로 받기
		Board board = null;
		
		//insert
		if (boardRequestDto.getId() == null) {
			board = boardRequestDto.toEntity(member);
			boardRepository.save(board);
		}
		
		//update
		else {
            board = boardRepository.findById(boardRequestDto.getId()).get();
			board.update(boardRequestDto.getTitle(),boardRequestDto.getCategory(), boardRequestDto.getContent());
		}
		
		//파일 저장
		fileService.saveFile(boardRequestDto, board.getId());
		
		
		return board.getId();
	}

	/*
	 * selectBoardDetail 
	 * 상세조회
	 */
//	public Board selectBoardDetail(Long id) {
//	    Board board = boardRepository.findById(id).get();
//	    board.updateViewCount(board.getViewCount());
//	    boardRepository.save(board);
//		return board;
//	}

	public BoardResponseDto getBoard(Long boardId) {
		Optional<Board> getBoard = boardRepository.findById(boardId);
		Board board = getBoard.get();
	    board.updateViewCount(board.getViewCount());
	    boardRepository.save(board);	//조회수 저장

	    //reponse
		BoardResponseDto boardResponseDto = BoardResponseDto.builder()
		.id(boardId)
		.title(board.getTitle())
		.category(board.getCategory())
		.content(board.getContent()).build();
		
		return boardResponseDto;
	}
	
	//첨부파일 불러오기
	public List<BoardFileDto> getFile(Long boardId) {
		return customBoardRepository.selectBoardFileDetail(boardId);
	}
	
	//삭제
	public void deleteBoard(Long boardId) {
		Board board = boardRepository.findById(boardId).get();
		this.boardRepository.delete(board);
	}

	//게시판 목록 불러오기
	public Page<BoardResponseDto> selectBoardList(String searchVal, Pageable pageable, Category category) {
		return customBoardRepository.selectBoardList(searchVal, pageable, category);
	}
	

}
