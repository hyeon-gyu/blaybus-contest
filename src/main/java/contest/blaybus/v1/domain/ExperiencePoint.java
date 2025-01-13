package contest.blaybus.v1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ExperiencePoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int year; // 년도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 멤버와 1대다 관계 예상

    private long totalExp; // 해당 년도 누적 경험치
    private long half1Eval; // 상반기 인사평가 점수
    private long half2Eval; // 하반기 인사평가 점수
    private long jobQuestEval; // 직무 퀘스트 점수
    private long leaderQuestEval; // 리더 퀘스트 점수
    private long companyProjectScore; // 전사 프로젝트 점수

    @Builder
    public ExperiencePoint(Member member) {
        this.year = 0;
        this.totalExp = 0;
        this.half1Eval = 0;
        this.half2Eval = 0;
        this.jobQuestEval = 0;
        this.leaderQuestEval = 0;
        this.companyProjectScore = 0;
        this.assignMember(member);
    }


    // 연관관계 편의 메소드
    public void assignMember(Member member) {
        if(this.member != null) {
            this.member.getExperiencePoints().remove(this);
        }
        this.member = member; // exp -> member
        if (!member.getExperiencePoints().contains(this)) {
            member.getExperiencePoints().add(this); // member -> exp
        }
    }
}
