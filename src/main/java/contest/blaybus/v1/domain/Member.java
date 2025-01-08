package contest.blaybus.v1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.plaf.TableHeaderUI;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationNumber; // 사원번호
    private String password; // 비밀번호
    private String name; // 성함

    @Enumerated(EnumType.STRING)
    private Team team; // 소속 팀

    private String profileImg; // 캐릭터 url

    private int level; // 레벨
    private String effectiveDate; // 근무시작일

    public Member(String identificationNumber, String password) { // 신규 사원 등록
        this.identificationNumber = identificationNumber;
        this.password = password;
        this.effectiveDate = LocalDate.now().toString();
        this.level = 0;
    }
}
