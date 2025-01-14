package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long memberId);

    Optional<Member> findByIdentificationNumber(String identificationNumber);
}
