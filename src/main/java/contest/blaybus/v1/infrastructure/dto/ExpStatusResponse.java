package contest.blaybus.v1.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpStatusResponse {

    private String currentLevel;
    private long totalExp;
    private long lastYearExp;
    private long thisYearExp;
    private long expForLevelup;
    private long remainingExp;

    public ExpStatusResponse(String currentLevel, long totalExp, long lastYearExp, long thisYearExp, long expForLevelUp, long remainingExp) {
        this.currentLevel = currentLevel;
        this.totalExp = totalExp;
        this.lastYearExp = lastYearExp;
        this.thisYearExp = thisYearExp;
        this.expForLevelup = expForLevelUp;
        this.remainingExp = remainingExp;
    }
}
