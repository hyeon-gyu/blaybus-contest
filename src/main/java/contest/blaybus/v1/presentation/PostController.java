package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.PostService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.PostListResponseDTO;
import contest.blaybus.v1.infrastructure.dto.PostResponseDTO;
import contest.blaybus.v1.presentation.dto.CreatePostRequestDTO;
import contest.blaybus.v1.presentation.dto.UpdatePostRequestDTO;
import contest.blaybus.v1.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글", description = "게시글 관련 API입니다.")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 작성")
    @PostMapping
    public ApiResponse<PostResponseDTO> createPost(@RequestBody CreatePostRequestDTO requestDTO) {
        Long adminId = SecurityUtil.getCurrentAdmin().getId();
        return ApiResponse.success(postService.createPost(requestDTO, adminId));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ApiResponse<PostResponseDTO> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestDTO requestDTO
    ) {
        Long adminId = SecurityUtil.getCurrentAdmin().getId();
        return ApiResponse.success(postService.updatePost(adminId, postId, requestDTO));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        Long adminId = SecurityUtil.getCurrentAdmin().getId();
        postService.deletePost(adminId, postId);
        return ApiResponse.success(null);
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID를 받아 게시글을 조회합니다.")
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO> singlePost(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPostById(postId));
    }

    @Operation(summary = "게시글 전체 목록 조회", description = "게시글 전체 목록을 최신순 또는 오래된 순으로 조회합니다.")
    @GetMapping
    public ApiResponse<PostListResponseDTO> getAllPosts(
            @Parameter(description = "정렬 기준 LATEST, OLDEST 중 하나입니다. 기본값:LATEST(최신순)", example = "LATEST")
            @RequestParam(required = false, defaultValue = "LATEST") String sort
    ) {
        return ApiResponse.success(postService.getAllPosts(sort));
    }

}
