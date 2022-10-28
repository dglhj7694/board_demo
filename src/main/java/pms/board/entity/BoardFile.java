package pms.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BoardFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_file_id")
	private Long id; // 번호

	private Long boardId;
	private String delYn;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	@Builder
	public BoardFile(Long boardId, Long fileId, String delYn, File file) {
		this.boardId = boardId;
		this.delYn = "N";
		this.file = file;
	}

	public BoardFile delete(String delYn) {
		this.delYn = delYn;
		return this;
	}
}