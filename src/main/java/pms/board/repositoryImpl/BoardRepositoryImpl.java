package pms.board.repositoryImpl;

import static pms.board.entity.QBoard.board;
import static pms.board.entity.QBoardFile.boardFile;
import static pms.board.entity.QMember.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import pms.board.dto.BoardFileResponseDto;
import pms.board.dto.BoardResponseDto;
import pms.board.dto.QBoardFileResponseDto;
import pms.board.dto.QBoardResponseDto;
import pms.board.entity.Category;
import pms.board.repository.CustomBoardRepository;

@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {

	private final JPAQueryFactory jpaQueryFactory;

	//QBoard board = board;
	//QMember member = member;

	public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	//게시판 목록
	@Override
	public Page<BoardResponseDto> selectBoardList(String searchVal, Pageable pageable, Category category) {
		List<BoardResponseDto> content = getBoardDtos(searchVal, pageable, category);
		Long count = getCount(searchVal,category);
		return new PageImpl<>(content, pageable, count);
	}

	//페이징 count
	private Long getCount(String searchVal, Category category) {
		Long count = jpaQueryFactory
				.select(board.count())
				.from(board)
				.where(containsSearch(searchVal))
				.where(board.category.eq(category))
				.fetchOne();
		return count;
	}
	
	//%키워드% 조회
	private BooleanExpression containsSearch(String searchVal) {
		return searchVal != null ? board.title.contains(searchVal) : null; 
	}

	//게시판 페이징 목록
	private List<BoardResponseDto> getBoardDtos(String searchVal, Pageable pageable, Category category){
        List<BoardResponseDto> content = jpaQueryFactory
                .select(new QBoardResponseDto(
                         board.id
                        ,board.title
                        ,board.category
                        ,board.content
                        ,board.regDate
                        ,board.uptDate
                        ,board.viewCount
                        ,member.username))
                .from(board)
                .leftJoin(board.member, member)
                .where(containsSearch(searchVal))
                .where(board.category.eq(category))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())	//페이지 번호 (0부터 시작)
                .limit(pageable.getPageSize())	//페이지 사이즈
                .fetch();
        return content;
    }

	//게시물 첨부파일 리스트
	 @Override
	    public List<BoardFileResponseDto> selectBoardFileDetail(Long boardId) {
	        List<BoardFileResponseDto> content = jpaQueryFactory
	                .select(new QBoardFileResponseDto(
	                         boardFile.file.id
	                        ,boardFile.file.id
	                        ,boardFile.file.originFileName
	                        ,boardFile.file.size
	                        ,boardFile.file.extension
	                ))
	                .from(boardFile)
	                .leftJoin(boardFile.file)
	                .where(boardFile.boardId.eq(boardId))
	                .where(boardFile.delYn.eq("N"))
	                .fetch();
	        return content;
	    }
}
