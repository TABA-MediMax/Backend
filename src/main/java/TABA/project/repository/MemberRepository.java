package TABA.project.repository;

import TABA.project.domain.Image;
import TABA.project.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);

}
