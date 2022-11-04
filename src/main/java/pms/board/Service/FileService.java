package pms.board.Service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pms.board.dto.BoardFileDto;
import pms.board.dto.BoardFileRequestDto;
import pms.board.dto.BoardRequestDto;
import pms.board.dto.FileDto;
import pms.board.entity.BoardFile;
import pms.board.repository.BoardFileRepository;
import pms.board.repository.FileRepository;
import pms.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
	/*
	 * upload: path: C:/multipart/file/
	 */
	@Value("${upload.path}")
	private String uploadDir;

	private final FileRepository fileRepository;

    private final BoardFileRepository boardFileRepository;

    private final MemberRepository memberRepository;
	/**
	 * @Method : saveFile
	 */
	@Transactional
	public Map<String, Object> saveFile(BoardRequestDto boardRequestDto, Long boardId) throws Exception {
		List<MultipartFile> multipartFile = boardRequestDto.getMultipartFile();

		//결과 map
		Map<String, Object> result = new HashMap<String, Object>();

		//파일 시퀀스 list
		List<Long> fileIds = new ArrayList<Long>();


        try {
            if (multipartFile != null) {
                if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
                    for (MultipartFile file1 : multipartFile) {
                        String originalFileName = file1.getOriginalFilename();    //오리지날 파일명
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
                        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

                        File targetFile = new File(uploadDir + savedFileName);

                        //초기값으로 fail 설정
                        result.put("result", "FAIL");

                        FileDto fileDto = FileDto.builder()
                                .originFileName(originalFileName)
                                .savedFileName(savedFileName)
                                .uploadDir(uploadDir)
                                .extension(extension)
                                .size(file1.getSize())
                                .contentType(file1.getContentType())
                                .build();
                        
						// insert
						pms.board.entity.File file = fileDto.toEntity();
						Long fileId = insertFile(file);
						log.info("fileId = {}", fileId);

						try {
							InputStream fileStream = file1.getInputStream();
							FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일저장
							
							//배열에 담기
							fileIds.add(fileId);
							result.put("fileIdxs", fileIds.toString());
							result.put("result", "OK");

							
						} catch (Exception e) {
							//파일삭제
							FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
							e.printStackTrace();
							result.put("result", "FAIL");
							break;

						}
						BoardFileRequestDto boardFileRequestDto = BoardFileRequestDto.builder()
								.boardId(boardId)
								.build();
						BoardFile boardFile = boardFileRequestDto.toEntity(file);
						insertBoardFile(boardFile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	//파일 저장 db
	/**
	 * @Method : insertFile
	 */
	@Transactional
	private Long insertFile(pms.board.entity.File file) {
		return fileRepository.save(file).getId();
	}

	/**
	 * @Method : insertBoardFile
	 */
	@Transactional
	private Long insertBoardFile(BoardFile boardFile) {
		return boardFileRepository.save(boardFile).getId();
	}
	
	
    /**
     * @Method : deleteBoardFile
     */
    @Transactional
    public BoardFile deleteBoardFile(Long boardFileId){
        BoardFile boardFile = boardFileRepository.findById(boardFileId).get();

        //삭제
        boardFile.delete("Y");
        return boardFile;
    }
    
    
}
