package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;

import java.util.List;


public interface ExpService {

    ExpStatusResponse getExpStatus(Long memberId);

    RecentExpInfoResponse getRecentExpInfo(Long memberId);

    List<RecentExpInfoResponse> getRecentExpInfoList(Long memberId);
}
