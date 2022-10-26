package pms.board.repositoryImpl;

import static pms.board.entity.QBoard.board;
import static pms.board.entity.QMember.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import pms.board.dto.BoardDto;
import pms.board.dto.QBoardDto;
import pms.board.repository.CustomBoardRepository;

@Repository
public class BoardRepositoryImpl implements CustomBoardRepository {

	private final JPAQueryFactory jpaQueryFactory;

	//QBoard board = board;
	//QMember member = member;

	public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public Page<BoardDto> selectBoardList(String searchVal, Pageable pageable) {
		List<BoardDto> content = getBoardMemberDtos(searchVal, pageable);
		Long count = getCount(searchVal);
		return new PageImpl<>(content, pageable, count);
	}

	private Long getCount(String searchVal) {
		// TODO Auto-generated method stub
		Long count = jpaQueryFactory
				.select(board.count())
				.from(board)
				.where(containsSearch(searchVal))
				.fetchOne();
		return count;
	}
	
	//%키워드% 조회
	private BooleanExpression containsSearch(String searchVal) {
		return searchVal != null ? board.title.contains(searchVal) : null; 
	}

	private List<BoardDto> getBoardMemberDtos(String searchVal, Pageable pageable) {
		// TODO Auto-generated method stub
		List<BoardDto> content = jpaQueryFactory
				.select(new QBoardDto(
						board.id
						,board.title
						,board.content
						,board.regDate
						,board.uptDate
						,board.viewCount
						,member.username))
				.from(board)
				.leftJoin(board.member, member)
				.orderBy(board.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		return content;
	}

	
}
