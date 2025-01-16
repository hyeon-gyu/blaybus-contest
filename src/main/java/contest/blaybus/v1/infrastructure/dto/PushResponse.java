package contest.blaybus.v1.infrastructure.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PushResponse {

    private Long id;
    private String category;
    private String title;
    private String content;
    private String time; // ex) n 시간 전 | 24.1.21 형식
}
