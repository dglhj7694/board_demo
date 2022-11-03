package pms.board;


import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pms.board.entity.Authority;
import pms.board.entity.Board;
import pms.board.entity.Category;
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
            List<Board> boardList = boardRepository.findAll();
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
            if (boardList.size()==0) {
            	for (int i = 0; i < 21; i++) {
    				Board board = Board.builder()
    						.title("공지"+i)
    						.content("content"+i)
    						.category(Category.NOTICE)
    						.build();
                	boardRepository.save(board);

				}
            	for (int i = 0; i < 43; i++) {
    				Board board = Board.builder()
    						.title("업무"+i)
    						.content("content"+i)
    						.category(Category.WORK)
    						.build();
                	boardRepository.save(board);
				}
			}
        }
    }
}