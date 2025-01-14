package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.presentation.NewPwdDTO;

public interface MemberService {

    MemberInfoResponse getMemberInfo(Long memberId);

    String changePwd(NewPwdDTO dto);

    Boolean checkDupPwd(NewPwdDTO dto);
}
