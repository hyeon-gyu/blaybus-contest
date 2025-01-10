package contest.blaybus.v1.application;

import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.Post;
import contest.blaybus.v1.domain.SortOrder;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.PostRepository;
import contest.blaybus.v1.infrastructure.dto.PostListResponseDTO;
import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final AdminRepository adminRepository;


    // 게시글 생성
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

    // 게시글 삭제
    @Transactional
    @Override
    public void deletePost(Long adminId, Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found with ID: " + postId);
        }

        postRepository.deleteById(postId);
    }

    // 게시글 단건 조회
    @Transactional
    @Override
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found with ID: " + postId));

        return PostResponseDTO.fromEntity(post);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    @Override
    public PostListResponseDTO getAllPosts(String sort) {
        SortOrder sortOrder;
        try {
            sortOrder = SortOrder.valueOf(sort.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid sort order. Use 'LATEST' or 'OLDEST'.");
        }

        List<Post> posts;
        if (sortOrder == SortOrder.LATEST) {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByOrderByCreatedAtAsc();
        }

        List<PostResponseDTO> postResponseList = posts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return PostListResponseDTO.builder()
                .count(postResponseList.size())
                .posts(postResponseList)
                .build();
    }
}