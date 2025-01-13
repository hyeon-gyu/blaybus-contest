package contest.blaybus.v1.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestDTO {
    @NotNull
    private String title; // 제목

    @NotNull
    private String content; // 내용

}
