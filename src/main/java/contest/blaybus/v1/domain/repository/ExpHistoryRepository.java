package contest.blaybus.v1.domain.repository;


import contest.blaybus.v1.domain.ExperiencePointHistory;
import contest.blaybus.v1.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpHistoryRepository extends JpaRepository<ExperiencePointHistory, Long> {

    Optional<ExperiencePointHistory> findFirstByMemberByDateDesc(Member member);

    Page<ExperiencePointHistory> findByMember(Member member, Pageable pageable);
}
