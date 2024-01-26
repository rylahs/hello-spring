package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }


    @Test
    @DisplayName("저장 테스트")
    void save() {
        // given
        Member member = new Member();
        member.setName("Rylah");

        // when
        repository.save(member);

        // then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    @DisplayName("ID 검색 테스트")
    void findById() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Vindictus");
        repository.save(member2);

        // when
        Member result1 = repository.findById(member1.getId()).get();
        Member result2 = repository.findById(member2.getId()).get();


        // then
        assertThat(result1).isEqualTo(member1);
        assertThat(result2).isEqualTo(member2);
    }

    @Test
    @DisplayName("이름 검색 테스트")
    void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Vindictus");
        repository.save(member2);

        // when
        Member result1 = repository.findByName("Rylah").get();
        Member result2 = repository.findByName("Vindictus").get();


        // then
        assertThat(result1).isEqualTo(member1);
        assertThat(result2).isEqualTo(member2);
    }

    @Test
    @DisplayName("모두 불러오기 테스트")
    void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("Rylah");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Vindictus");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}