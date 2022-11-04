package pms.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pms.board.entity.BoardFile;
import pms.board.entity.File;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFileRequestDto {
	private Long boardFileId;
	private Long id;
	private Long fileId;
	private Long boardId;
	private String originFileName;
	private Long size;
	private String extension;
	
	public BoardFile toEntity(File file) {
		return BoardFile
				.builder()
				.boardId(boardId)
				.file(file)
				.build();
	}
	
	@Builder
	public BoardFileRequestDto(Long boardId) {
		this.boardId = boardId;
	}
}
