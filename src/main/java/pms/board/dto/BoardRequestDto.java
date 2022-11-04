package pms.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import pms.board.entity.Board;
import pms.board.entity.Category;
import pms.board.entity.Member;

/*Board에 추가할 데이터를 입력할 때 사용*/
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

	private Long id; // 시퀀스
	@NotEmpty(message = "제목은 필수입니다.")
	private String title; // 제목
	@NotEmpty(message = "카테고리 선택은 필수입니다.")
	private Category category; // 카테고리
	private String content; // 내용
	private LocalDateTime regDate; // 등록 날짜
	private LocalDateTime uptDate; // 수정 날짜
	private Long viewCount; // 조회수
	private String username; // 사용자 이름
	private List<MultipartFile> multipartFile;

	
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
