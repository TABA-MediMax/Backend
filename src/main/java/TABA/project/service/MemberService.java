package TABA.project.service;

import TABA.project.domain.Member;
import TABA.project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signUp(String nickName, String email) {
        Member newMember = new Member(nickName, email);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public boolean isMemberExists(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }




    // ... 기타 필요한 메서드 구현
}

