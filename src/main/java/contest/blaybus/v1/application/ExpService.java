package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;
import org.springframework.data.domain.Page;


public interface ExpService {

    ExpStatusResponse getExpStatus(Long memberId);

    RecentExpInfoResponse getRecentExpInfo(Long memberId);

    Page<RecentExpInfoResponse> getRecentExpInfoList(int page, int size, Long memberId);
}
