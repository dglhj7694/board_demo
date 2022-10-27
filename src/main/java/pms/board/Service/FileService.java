package pms.board.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pms.board.dto.BoardDto;
import pms.board.repository.FileRepository;

@Service
@RequiredArgsConstructor
public class FileService {
	/*
	 * upload: path: C:/multipart/file/
	 */
    @Value("${upload.path}")
	private String uploadDir;
    
    private final FileRepository fileRepository;
	
    @Transactional
	public void saveFile(BoardDto boardDto) {
		// TODO Auto-generated method stub
		
	}

}
