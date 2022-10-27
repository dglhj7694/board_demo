package pms.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pms.board.entity.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
