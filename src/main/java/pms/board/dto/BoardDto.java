package pms.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import pms.board.entity.Board;
import pms.board.entity.Category;
import pms.board.entity.Member;

@Data
public class BoardDto {

	private Long id; // 시퀀스

	@NotEmpty(message = "제목은 필수입니다.")
	private String title; // 제목
	private Category category; // 카테고리
	private String content; // 내용
	private LocalDateTime regDate; // 등록 날짜
	private LocalDateTime uptDate; // 수정 날짜
	private Long viewCount; // 조회수
	private String username; // 사용자 이름

	private List<MultipartFile> multipartFile;

	public BoardDto() {

	}

	@Builder
	public BoardDto(Long id, String title, Category category, String content) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
	}

	// querydsl Select 대상을 지정
	@QueryProjection
	public BoardDto(Long id, String title, Category category, String content, LocalDateTime regDate, LocalDateTime uptDate, Long viewCount,
			String username) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.content = content;
		this.regDate = regDate;
		this.uptDate = uptDate;
		this.viewCount = viewCount;
		this.username = username;
	}

	public Board toEntity(Member member) {
		return Board
				.builder()
				.member(member)
				.title(title)
				.category(category)
				.content(content)
				.build();
	}
}