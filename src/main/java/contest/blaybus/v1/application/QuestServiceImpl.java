package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.JobQuestMonth;
import contest.blaybus.v1.domain.JobQuestWeek;
import contest.blaybus.v1.domain.repository.JobQuestMonthRepository;
import contest.blaybus.v1.domain.repository.JobQuestWeekRepository;
import contest.blaybus.v1.infrastructure.dto.QuestMonthResponse;
import contest.blaybus.v1.infrastructure.dto.QuestWeekResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestServiceImpl implements QuestService {

    private final JobQuestWeekRepository jobQuestWeekRepository;
    private final JobQuestMonthRepository jobQuestMonthRepository;

    @Override
    public List<QuestWeekResponse> getMyQuestWeek(final Long memberId) {
        List<JobQuestWeek> jobQuestWeeks = jobQuestWeekRepository.findByMemberId(memberId);
        return jobQuestWeeks.stream()
                .map(
                        jobQuestWeek -> {
                            int value = calculate(jobQuestWeek.getProductivity());
                            return QuestWeekResponse.from(jobQuestWeek.getWeek(), value);
                        }
                ).toList();
    }

    @Override
    public List<QuestMonthResponse> getMyQuestMonth(final Long memberId) {
        List<JobQuestMonth> jobQuestMonths = jobQuestMonthRepository.findByMemberId(memberId);
        return jobQuestMonths.stream()
                .map(
                        jobQuestMonth -> {
                            int value = calculate(jobQuestMonth.getProductivity());
                            return QuestMonthResponse.from(jobQuestMonth.getMonth(), value);
                        }
                ).toList();
    }

    private static int calculate(float value) {
        if (value >= 5.1) {
            return 80;
        } else if (value >= 4.3) {
            return 40;
        }
        return 0;
    }
}
