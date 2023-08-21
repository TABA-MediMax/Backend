package TABA.project.service;

import TABA.project.domain.Member;
import TABA.project.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveToSqliteDB() {
        Member member = new Member();
        member.setEmail("Email!!!!!!");
        member.setAgreedToAll(true);
        member.setAgreedToOptionalThirdParty(false);
        member.setAgreedToServiceAccess(false);
        memberRepository.save(member);
    }
}