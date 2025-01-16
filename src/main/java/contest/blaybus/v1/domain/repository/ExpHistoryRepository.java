package contest.blaybus.v1.domain.repository;


import contest.blaybus.v1.domain.ExpType;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import contest.blaybus.v1.domain.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpHistoryRepository extends JpaRepository<ExperiencePointHistory, Long> {

    Optional<ExperiencePointHistory> findFirstByMemberOrderByDateDesc(Member member);

    List<ExperiencePointHistory> findByMember(Member member, Sort sort);

    List<ExperiencePointHistory> findByMemberAndExpType(Member member, ExpType expType, Sort sort);
}
