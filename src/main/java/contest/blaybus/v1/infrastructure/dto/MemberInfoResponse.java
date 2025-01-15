package contest.blaybus.v1.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import contest.blaybus.v1.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponse {

    private Long memberId;
    private String identificationNumber; // 사원번호
    private String team; // 소속
    private String name; // 성함
    private String character; // 캐릭터 URL
    private Date effectiveDate; // 근무시작일
    private String level; // 레벨

    public static MemberInfoResponse fromEntity(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .identificationNumber(member.getIdentificationNumber())
                .team(member.getTeam().getDisplayName())
                .name(member.getName())
                .character(member.getProfileImg())
                .effectiveDate(member.getEffectiveDate())
                .level(member.getLevel())
                .build();
    }
}
