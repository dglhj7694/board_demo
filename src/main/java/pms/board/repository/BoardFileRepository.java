package pms.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pms.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>{

}
