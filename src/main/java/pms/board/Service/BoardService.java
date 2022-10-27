package pms.board.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
	public Long saveBoard(BoardDto boardDto) {
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
		fileService.saveFile(boardDto);
		return board.getId();
	}

	/*
	 * selectBoardDetail 
	 * 상세조회
	 */
	public Board selectBoardDetail(Long id) {
		/* Board board = boardRepository.findById(id).get(); */
		return boardRepository.findById(id).get();
	}
}
