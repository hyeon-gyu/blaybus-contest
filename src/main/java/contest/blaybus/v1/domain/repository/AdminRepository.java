package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByIdentificationNumber(String identificationNumber);
}
