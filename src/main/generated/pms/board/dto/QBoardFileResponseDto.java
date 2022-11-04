package pms.board.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * pms.board.dto.QBoardFileResponseDto is a Querydsl Projection type for BoardFileResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardFileResponseDto extends ConstructorExpression<BoardFileResponseDto> {

    private static final long serialVersionUID = 1808297131L;

    public QBoardFileResponseDto(com.querydsl.core.types.Expression<Long> boardFileId, com.querydsl.core.types.Expression<Long> fileId, com.querydsl.core.types.Expression<String> originFileName, com.querydsl.core.types.Expression<Long> size, com.querydsl.core.types.Expression<String> extension) {
        super(BoardFileResponseDto.class, new Class<?>[]{long.class, long.class, String.class, long.class, String.class}, boardFileId, fileId, originFileName, size, extension);
    }

}

