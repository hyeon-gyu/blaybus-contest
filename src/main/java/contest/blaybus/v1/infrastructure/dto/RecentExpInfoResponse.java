package contest.blaybus.v1.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentExpInfoResponse {

    private LocalDate date;
    private String content;
    private long exp;

    public RecentExpInfoResponse(LocalDate date, String content, int exp) {
        this.date = date;
        this.content = content;
        this.exp = exp;
    }

    public static RecentExpInfoResponse fromEntity(ExperiencePointHistory history) {
        return RecentExpInfoResponse.builder()
                .date(history.getDate())
                .content(history.getContent())
                .exp(history.getPoint())
                .build();
    }
}
