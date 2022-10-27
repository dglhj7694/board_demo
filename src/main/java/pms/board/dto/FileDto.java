package pms.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pms.board.entity.File;

@Data
@NoArgsConstructor
public class FileDto {

	private Long id;	//id
	private String originFileName;	//원본 파일 명
	private String savedFileName;	//저장된 파일 명
	private String uploadDir;	//경로명
	private String extension;	//확장자
	private Long size;	//파일 크기
	private String contentType;	//contentType
	
	@Builder
	public FileDto(Long id, String originFileName, String savedFileName, String uploadDir, String extension, Long size,
			String contentType) {
		this.id = id;
		this.originFileName = originFileName;
		this.savedFileName = savedFileName;
		this.uploadDir = uploadDir;
		this.extension = extension;
		this.size = size;
		this.contentType = contentType;
	}
	
	//dto >> entity
	public File toEntity() {
		return File.builder()
				.originFileName(originFileName)
				.savedFileName(savedFileName)
				.uploadDir(uploadDir)
				.extension(extension)
				.size(size)
				.contentType(contentType)
				.build();
	}
}
