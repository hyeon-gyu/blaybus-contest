package contest.blaybus.v1.domain;


import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationNumber; // 사원번호
    private String password; // 비밀번호
    private String name; // 성함

    @Enumerated(EnumType.STRING)
    private Team team; // 소속 팀

    private int jobGroup; // 직무 그룹

    @Enumerated(EnumType.STRING)
    private JobType jobType; // 직군

    @Nullable
    private String profileImg; // 캐릭터 url

    private String level; // 레벨
    private LocalDate effectiveDate; // 근무시작일

    private long totalExp; // 총 누적 경험치

    @OneToMany(mappedBy = "member") // cascade 제거 : 멤버가 삭제되어도 기록은 유지
    private List<ExperiencePoint> experiencePoints = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ExperiencePointHistory> experiencePointHistories = new ArrayList<>();

    @Builder
    public Member(String identificationNumber, String password, String name, JobType jobType) { // 신규 사원 등록
        this.identificationNumber = identificationNumber;
        this.password = password;
        this.name = name;
        this.effectiveDate = LocalDate.now();
        this.jobType = jobType;
        this.level = jobType.getStartLevel(); // 직군에 따른 시작 레벨 선정
        this.totalExp = jobType.getStartExp();
    }

    public void updateLevel(String level) {
        this.level = level;
    }

}
