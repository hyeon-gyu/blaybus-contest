package contest.blaybus.v1.domain.repository;


import contest.blaybus.v1.domain.ExperiencePoint;
import contest.blaybus.v1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpRepository extends JpaRepository<ExperiencePoint, Long> {

    @Query("SELECT ep.totalExp FROM ExperiencePoint ep WHERE ep.member = :member AND ep.year = :year")
    Optional<Long> findTotalExpByMemberAndYear(@Param("member") Member member, @Param("year") int year);

    @Query("SELECT COALESCE(SUM(ep.totalExp), 0) FROM ExperiencePoint ep WHERE ep.member = :member AND ep.year < :currentYear")
    Long getSumTotalExpUtilLastYear(@Param("member") Member member, @Param("currentYear") int currentYear);

}
