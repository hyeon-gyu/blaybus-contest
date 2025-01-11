package contest.blaybus.v1.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentExpInfoResponse {

    private Date date;
    private String content;
    private long exp;

    public RecentExpInfoResponse(Date date, String content, long exp) {
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
