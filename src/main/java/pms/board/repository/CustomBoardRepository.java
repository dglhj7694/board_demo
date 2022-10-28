package pms.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pms.board.dto.BoardDto;
import pms.board.dto.BoardFileDto;

public interface CustomBoardRepository {
	//게시판 페이징 목록
	Page<BoardDto> selectBoardList(String searchVal, Pageable pageable);

	//게시판 상세 첨부파일 조회
	List<BoardFileDto> selectBoardFileDetail(Long boardId);
}