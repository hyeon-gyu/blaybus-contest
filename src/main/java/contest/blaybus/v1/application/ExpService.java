package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.ExpBarResponse;
import contest.blaybus.v1.infrastructure.dto.ExpHistoryResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;

import java.util.List;


public interface ExpService {

    ExpStatusResponse getExpStatus(Long memberId);

    RecentExpInfoResponse getRecentExpInfo(Long memberId);

    ExpBarResponse getExpBar(Long memberId);

    ExpBarResponse getExpBarThisYear(Long memberId);

    List<ExpHistoryResponse> getExpHistoryPerCategory(Long memberId, String category, String order);
}
