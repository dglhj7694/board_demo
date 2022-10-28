package pms.board.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * pms.board.dto.QBoardFileDto is a Querydsl Projection type for BoardFileDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardFileDto extends ConstructorExpression<BoardFileDto> {

    private static final long serialVersionUID = -1122956884L;

    public QBoardFileDto(com.querydsl.core.types.Expression<Long> boardFileId, com.querydsl.core.types.Expression<Long> fileId, com.querydsl.core.types.Expression<String> originFileName, com.querydsl.core.types.Expression<Long> size, com.querydsl.core.types.Expression<String> extension) {
        super(BoardFileDto.class, new Class<?>[]{long.class, long.class, String.class, long.class, String.class}, boardFileId, fileId, originFileName, size, extension);
    }

}

