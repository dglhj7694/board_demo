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
	
	@Transactional
	public Long saveBoard(BoardDto boardDto) {
		List<Member> memberList = memberRepository.findAll();
		Member member = memberList.get(0);
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
		
		return board.getId();
	}
}
