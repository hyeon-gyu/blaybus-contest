package contest.blaybus.v1.domain.repository;

import contest.blaybus.v1.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtAsc();

    List<Post> findAllByOrderByCreatedAtDesc();
}
