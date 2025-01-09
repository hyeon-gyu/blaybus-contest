package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;

public interface ExpService {

    ExpStatusResponse getExpStatus(Long memberId);

}
