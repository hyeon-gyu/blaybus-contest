package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.QuestWeekResponse;
import java.util.List;

public interface QuestService {
    List<QuestWeekResponse> getMyQuestWeek(Long memberId);
}
