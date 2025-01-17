package contest.blaybus.v1.infrastructure.dto;

import contest.blaybus.v1.domain.ExpType;
import contest.blaybus.v1.domain.ExperiencePointHistory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExpHistoryResponse {

    private String expType;
    private String date;
    private String content;
    private long point;
    private String coin;

    public ExpHistoryResponse(String expType, String date, String content, long point, String coin) {
        this.expType = expType;
        this.date = date;
        this.content = content;
        this.point = point;
        this.coin = coin;
    }

    public static ExpHistoryResponse fromEntity(ExperiencePointHistory history) {
        return ExpHistoryResponse.builder()
                .expType(history.getExpType().getDescription())
                .date(history.getDate().toString())
                .content(history.getContent())
                .point(history.getPoint())
                .coin(history.getCoin())
                .build();
    }
}
