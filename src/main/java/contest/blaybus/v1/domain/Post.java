package contest.blaybus.v1.domain;

import contest.blaybus.v1.presentation.dto.UpdatePostRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void edit(UpdatePostRequestDTO postUpdateRequest) {
        String title = postUpdateRequest.getTitle();
        if (StringUtils.hasText(title)) {
            this.title = postUpdateRequest.getTitle();
        }
        String content = postUpdateRequest.getContent();
        if (StringUtils.hasText(content)) {
            this.content = content;
        }
    }
}
