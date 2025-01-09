package contest.blaybus.v1.domain.repository;


import contest.blaybus.v1.domain.ExperiencePointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpHistoryRepository extends JpaRepository<ExperiencePointHistory, Long> {
}
