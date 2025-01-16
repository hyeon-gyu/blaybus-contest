package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMemberOrderByDateDesc(Member member);
}
