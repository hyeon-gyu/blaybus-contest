package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
