package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.PostListResponseDTO;
import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;
import contest.blaybus.v1.presentation.dto.UpdatePostRequestDTO;

import java.io.IOException;

public interface PostService {
    PostResponseDTO createPost(CreatePostRequestDTO requestDTO, Long adminId) throws IOException;

    PostResponseDTO getPostById(Long postId);

    PostListResponseDTO getAllPosts(String sort);

    void deletePost(Long adminId, Long postId);

    PostResponseDTO updatePost(Long adminId, Long postId, UpdatePostRequestDTO requestDTO);
}
