package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Team;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long memberId);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM member WHERE personal_id = :personalId) THEN 'true' ELSE 'false' END", nativeQuery = true)
    Boolean existsByPersonalId(@Param("personalId") String personalId);

    List<Member> findByTeam(Team team);

    List<Member> findByIdentificationNumber(String number);
    List<Member> findByName(String name);
}
