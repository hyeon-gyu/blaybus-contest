package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.Post;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.PostRepository;
import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AdminRepository adminRepository;

    @Transactional
    @Override
    public PostResponseDTO createPost(CreatePostRequestDTO requestDTO, Long adminId) {
        // Admin ID로 Admin 엔티티 조회
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with ID: " + adminId));

        // 게시글 생성
        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .admin(admin) // 작성자 Admin 설정
                .build();

        Post savedPost = postRepository.saveAndFlush(post);

        return PostResponseDTO.fromEntity(savedPost);
    }
}