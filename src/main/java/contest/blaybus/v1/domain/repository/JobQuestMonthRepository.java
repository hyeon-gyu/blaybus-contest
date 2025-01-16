package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.JobQuestMonth;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobQuestMonthRepository extends JpaRepository<JobQuestMonth, Long> {
    List<JobQuestMonth> findByMemberId(Long memberId);
}
