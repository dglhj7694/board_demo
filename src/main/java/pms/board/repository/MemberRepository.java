package pms.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pms.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
