package contest.blaybus.v1.application;

import contest.blaybus.v1.presentation.dto.NewMemberDTO;

public interface AdminService {

    // 멤버 추가하기
    String addMember(NewMemberDTO dto);

    // 멤버 삭제하기
    // 멤버 정보 수정하기
}
