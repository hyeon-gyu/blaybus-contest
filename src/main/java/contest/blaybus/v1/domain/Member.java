package contest.blaybus.v1.domain;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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

    @Temporal(TemporalType.DATE)
    private Date effectiveDate; // 근무시작일


    private long totalExp; // 총 누적 경험치

    @OneToMany(mappedBy = "member") // cascade 제거 : 멤버가 삭제되어도 기록은 유지
    private List<ExperiencePoint> experiencePoints = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ExperiencePointHistory> experiencePointHistories = new ArrayList<>();

    @Builder
    public Member(String name, Team team, String identificationNumber, String pwd, JobType jobType, Date date) { // 신규 사원 등록
        this.name = name;
        this.team = team;
        this.identificationNumber = identificationNumber;
        this.password = pwd;
        this.effectiveDate = date;
        this.jobType = jobType;
        this.level = jobType.getStartLevel(); // 직군에 따른 시작 레벨 선정
        this.totalExp = jobType.getStartExp();
    }

    public void updateLevel(String level) {
        this.level = level;
    }
    public void updatePwd(String pwd) {this.password = pwd; }
    public void updateProfileImg(String url) {this.profileImg = url; }
}
