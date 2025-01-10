package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;

public interface PostService {
    PostResponseDTO createPost(CreatePostRequestDTO requestDTO, Long adminId);

    PostResponseDTO getPostById(Long postId);
}
