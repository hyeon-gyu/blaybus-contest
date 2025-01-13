package contest.blaybus.v1.infrastructure.dto;

import contest.blaybus.v1.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PostResponseDTO {
    private Long id;         // 게시글 ID
    private String title;    // 게시글 제목
    private String content;  // 게시글 내용
    private String author;   // 작성자(Admin) 이름
    private String createdAt; // 작성 시간

    public static PostResponseDTO fromEntity(Post post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author("관리자")
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
