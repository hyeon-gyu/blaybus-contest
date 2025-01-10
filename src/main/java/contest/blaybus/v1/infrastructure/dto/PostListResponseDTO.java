package contest.blaybus.v1.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostListResponseDTO {
    private int count;
    private List<PostResponseDTO> posts;
}
