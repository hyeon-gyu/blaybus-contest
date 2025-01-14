package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.presentation.dto.NewPwdDTO;
import contest.blaybus.v1.presentation.dto.NewProfileImageDTO;
import contest.blaybus.v1.presentation.dto.NewUuidDTO;

public interface MemberService {

    MemberInfoResponse getMemberInfo(Long memberId);

    String changePwd(NewPwdDTO dto);

    Boolean checkDupPwd(NewPwdDTO dto);

    String updateProfileImg(NewProfileImageDTO dto);

    String updateUuid(NewUuidDTO dto);
}
