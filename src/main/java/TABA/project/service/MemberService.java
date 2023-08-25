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

    public Member signUp(String nickname, String email) {
        Member newMember = new Member(nickname, email);
        return memberRepository.save(newMember);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public boolean isMemberExists(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

}

