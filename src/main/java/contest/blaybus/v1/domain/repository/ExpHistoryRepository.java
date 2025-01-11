package contest.blaybus.v1.domain.repository;


import contest.blaybus.v1.domain.ExperiencePointHistory;
import contest.blaybus.v1.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpHistoryRepository extends JpaRepository<ExperiencePointHistory, Long> {

    Optional<ExperiencePointHistory> findFirstByMemberOrderByDateDesc(Member member);

    Page<ExperiencePointHistory> findByMemberOrderByDateDesc(Member member, Pageable pageable);
}
