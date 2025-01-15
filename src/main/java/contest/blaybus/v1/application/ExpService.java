package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.ExpHistoryResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;

import java.util.List;


public interface ExpService {

    ExpStatusResponse getExpStatus(Long memberId);

    RecentExpInfoResponse getRecentExpInfo(Long memberId);

    Long getExpBar(Long memberId);

    Long getExpBarThisYear(Long memberId);

    List<ExpHistoryResponse> getExpHistoryPerCategory(Long memberId, String category, String order);
}
