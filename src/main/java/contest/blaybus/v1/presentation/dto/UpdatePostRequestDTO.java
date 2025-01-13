package contest.blaybus.v1.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequestDTO {
    @NotNull
    private String title; // 수정할 제목

    @NotNull
    private String content; // 수정할 내용
}
