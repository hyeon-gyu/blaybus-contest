package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.presentation.dto.CheckPwdDTO;
import contest.blaybus.v1.presentation.dto.NewPwdDTO;
import contest.blaybus.v1.presentation.dto.NewProfileImageDTO;
import contest.blaybus.v1.presentation.dto.NewUuidDTO;

public interface MemberService {

    MemberInfoResponse getMemberInfo(Long memberId);

    String changePwd(Long memberId, NewPwdDTO dto);

    Boolean checkDupPwd(Long memberId, CheckPwdDTO dto);

    String updateProfileImg(Long memberId, NewProfileImageDTO dto);

    String updateFcmToken(Long memberId, NewUuidDTO dto);
}
