package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;

public interface MemberService {

    MemberInfoResponse getMemberInfo(Long memberId);
}
