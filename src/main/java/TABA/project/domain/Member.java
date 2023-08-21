package TABA.project.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private boolean agreedToAll; // 전체 동의 여부

    private boolean agreedToOptionalThirdParty; // [선택] 카카오 개인정보 제3자 제공 동의

    private boolean agreedToServiceAccess; // [선택] 서비스 접근 권한 동의
}
