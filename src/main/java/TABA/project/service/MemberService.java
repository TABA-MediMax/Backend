package TABA.project.service;

import TABA.project.domain.Member;
import TABA.project.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void saveToSqliteDB(Member member){
        memberRepository.save(member);
    }
}
