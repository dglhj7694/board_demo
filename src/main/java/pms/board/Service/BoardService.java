package pms.board.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;
import pms.board.DataNotFoundException;
import pms.board.dto.BoardDto;
import pms.board.entity.Board;
import pms.board.entity.Member;
import pms.board.repository.BoardRepository;
import pms.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final FileService fileService;
	/* saveBoard
	 * 게시물 등록 */
	@Transactional
	public Long saveBoard(BoardDto boardDto) throws Exception {
		List<Member> memberList = memberRepository.findAll();
		Member member = memberList.get(0);	// 추후 로그인된 멤버로 받기
		Board board = null;
		
		//insert
		if (boardDto.getId() == null) {
			board = boardDto.toEntity(member);
			boardRepository.save(board);
		}
		
		//update
		else {
            board = boardRepository.findById(boardDto.getId()).get();
			board.update(boardDto.getTitle(), boardDto.getContent());
		}
		
		//파일 저장
		fileService.saveFile(boardDto, board.getId());
		
		
		return board.getId();
	}

	/*
	 * selectBoardDetail 
	 * 상세조회
	 */
	public Board selectBoardDetail(Long id) {
	    Board board = boardRepository.findById(id).get();
	    board.updateViewCount(board.getViewCount());
	    boardRepository.save(board);
		return board;
	}

	public Board getBoard(Long boardId) {
		Optional<Board> board = boardRepository.findById(boardId);
		if (board.isPresent()) {
			return board.get();
		}else {
			throw new DataNotFoundException("board not found");
		}
	}
	
	//삭제
	public void deleteBoard(Board board) {
		this.boardRepository.delete(board);
	}
	
}
