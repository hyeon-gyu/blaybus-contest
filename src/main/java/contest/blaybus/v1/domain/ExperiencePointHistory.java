package contest.blaybus.v1.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class ExperiencePointHistory { // 최근 획득한 경험치 조회용 ( 경험치 획득 히스토리 )

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Temporal(TemporalType.DATE)
    private Date date; // 경험치 획득 일자
    private String content; // 내용
    private long point; // 획득 경험치 수치

    public void assignMember(Member member) { // 연관관계 주인쪽에서 추가해주기
        if(this.member != null) {
            this.member.getExperiencePointHistories().remove(this);
        }
        this.member = member;
        if (!member.getExperiencePointHistories().contains(this)) {
            member.getExperiencePointHistories().add(this); // Member의 편의 메소드 호출
        }
    }

}
