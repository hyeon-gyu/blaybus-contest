package contest.blaybus.v1.infrastructure.dto;

import contest.blaybus.v1.domain.ExpType;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExpHistoryResponse {

    private ExpType expType;
    private String date;
    private String content;
    private long point;

    public ExpHistoryResponse(ExpType expType, String date, String content, long point) {
        this.expType = expType;
        this.date = date;
        this.content = content;
        this.point = point;
    }

    public static ExpHistoryResponse fromEntity(ExperiencePointHistory history) {
        return ExpHistoryResponse.builder()
                .expType(history.getExpType())
                .date(history.getDate().toString())
                .content(history.getContent())
                .point(history.getPoint())
                .build();
    }
}
