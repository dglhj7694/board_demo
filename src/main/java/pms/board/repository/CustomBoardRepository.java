package pms.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pms.board.dto.BoardDto;
import pms.board.dto.BoardFileDto;
import pms.board.dto.BoardResponseDto;
import pms.board.entity.Category;

public interface CustomBoardRepository {
	//BoardRepositoryImpl~
	 
	//게시판 페이징 처리된 리스트 목록
	Page<BoardResponseDto> selectBoardList(String searchVal, Pageable pageable, Category category);

	//게시판 상세 첨부파일 조회
	List<BoardFileDto> selectBoardFileDetail(Long boardId);
}