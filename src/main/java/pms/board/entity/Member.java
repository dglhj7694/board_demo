package pms.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;            //번호
    private String username;    //작성자
    private String password;    //비밀번호
    private String phoneNo;     //핸드폰번호
    private int age;         //나이

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Authority authority; //권한 [ROLE_USER, ROLE_ADMIN]

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Member(String username, String phoneNo, int age, Authority authority){
        this.username = username;
        this.phoneNo = phoneNo;
        this.age = age;
        this.authority = authority;
    }
}