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
    private String nextLevel; // 다음 레벨
    private long percent; // 다음 레벨까지 % 수치

    public ExpStatusResponse(String currentLevel, long totalExp, long lastYearExp, long thisYearExp, long expForLevelUp, long remainingExp, String nextLevel, long percent) {
        this.currentLevel = currentLevel;
        this.totalExp = totalExp;
        this.lastYearExp = lastYearExp;
        this.thisYearExp = thisYearExp;
        this.expForLevelup = expForLevelUp;
        this.remainingExp = remainingExp;
        this.nextLevel = nextLevel;
        this.percent = percent;
    }
}
