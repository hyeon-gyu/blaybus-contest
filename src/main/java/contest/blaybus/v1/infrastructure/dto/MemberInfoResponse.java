package contest.blaybus.v1.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import contest.blaybus.v1.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponse {

    private String identificationNumber; // 사원번호
    private String team; // 소속
    private String name; // 성함
    private String character; // 캐릭터 URL
    private String effectiveDate; // 근무시작일
    private int level; // 레벨

    public static MemberInfoResponse fromEntity(Member member) {
        return MemberInfoResponse.builder()
                .identificationNumber(member.getIdentificationNumber())
                .team(member.getTeam().getDisplayName())
                .name(member.getName())
                .character(member.getProfileImg())
                .effectiveDate(member.getEffectiveDate())
                .level(member.getLevel())
                .build();
    }
}
