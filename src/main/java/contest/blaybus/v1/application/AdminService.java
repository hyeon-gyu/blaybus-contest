package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Team;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import jakarta.persistence.EnumType;

import java.text.ParseException;
import java.util.List;

public interface AdminService {

    // 멤버 추가하기
    String addMember(NewMemberDTO dto) throws ParseException;

    // 멤버 삭제하기
    // 멤버 정보 수정하기

    // 소속 목록 조회
    List<String> getTeamList();

    // 직군 목록 조회
    List<String> getJobTypeList();
}
