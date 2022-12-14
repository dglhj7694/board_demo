package pms.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id; // 번호

	@Enumerated(EnumType.STRING)
	private Category category;// 카테고리 [NOTICE, WORK]

	private String title; // 제목
	private String content; // 내용	

	@CreatedDate
	private LocalDateTime regDate; // 등록 날짜

	@LastModifiedDate
	private LocalDateTime uptDate; // 수정 날짜

	private Long viewCount; // 조회수

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; // 작성자

	public Board update(String title, Category category, String content) {
		this.title = title;
		this.category = category;
		this.content = content;
		return this;
	}

	@Builder
	public Board(String title, Category category, String content, Member member) {
		this.title = title;
		this.category = category;
		this.content = content;
		this.viewCount = 0L;
		this.member = member;
	}

	public Board updateViewCount(Long viewCount) {
		this.viewCount = viewCount + 1;
		return this;
	}


}