package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // TestCase는 가장 편한 방법인 필드주입을 쓰기도 한다

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    @DisplayName("회원 가입")
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("MintRisha");
        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    @DisplayName("회원가입 중복 테스트")
    public void join_duplicate() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");


        // when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));


        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}
