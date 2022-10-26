package pms.board;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pms.board.entity.Authority;
import pms.board.entity.Member;
import pms.board.repository.BoardRepository;
import pms.board.repository.MemberRepository;


@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.userDBInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;
        public void userDBInit(){

            List<Member> memberList = memberRepository.findAll();
            if(memberList.size() == 0){
                Member member = Member.builder()
                        .username("관리자")
                        .phoneNo("010-1111-1111")
                        .age(26)
                        .authority(Authority.ROLE_ADMIN)
                        .build();
                //member 저장
                memberRepository.save(member);
            }
        }
    }
}