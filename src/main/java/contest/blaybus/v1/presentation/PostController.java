package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.PostService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글", description = "게시글 관련 API입니다.")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 작성", description = "Admin ID를 쿼리 파라미터로 받아 게시글을 작성하는 API입니다.(수정 예정)")
    @PostMapping
    public ApiResponse<PostResponseDTO> createPost(
            @Parameter(description = "작성자(Admin)의 ID", required = true)
            @RequestParam Long adminId,
            @RequestBody CreatePostRequestDTO requestDTO
    ) {
        return ApiResponse.success(postService.createPost(requestDTO, adminId));
    }
}
