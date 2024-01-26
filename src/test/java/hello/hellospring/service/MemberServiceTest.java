package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void join() {
        // given
        Member member = new Member();
        member.setName("Rylah");

        // when
        Long saveId = memberService.join(member);


        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    @DisplayName("중복 회원 가입 예외 테스트")
    void join_duplicate() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");

        Member member2 = new Member();
        member2.setName("Rylah");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }

    @Test
    @DisplayName("회원 모두 검색")
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");

        Member member2 = new Member();
        member2.setName("Vindictus");

        memberService.join(member1);
        memberService.join(member2);

        // when
        List<Member> result = memberService.findMembers();

        // then
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("회원 1명 검색")
    void findOne() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");

        Member member2 = new Member();
        member2.setName("Vindictus");

        memberService.join(member1);
        memberService.join(member2);

        // when
        Long res = memberService.findOne(member2.getId()).get().getId();

        // then
        assertThat(res).isEqualTo(member2.getId());
    }
}