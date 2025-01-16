package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.JobQuestWeek;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobQuestWeekRepository extends JpaRepository<JobQuestWeek, Long> {
    List<JobQuestWeek> findByMemberId(Long memberId);
}
