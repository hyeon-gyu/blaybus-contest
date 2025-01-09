package contest.blaybus.v1.util;

import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Member;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LevelCheckUtil {

    public static final Map<JobType, List<LevelInfo>> levelInfoMap = new EnumMap<>(JobType.class);

    static {
        levelInfoMap.put(JobType.F, Arrays.asList(
                new LevelInfo("F1-Ⅰ", 0),
                new LevelInfo("F1-Ⅱ", 13500),
                new LevelInfo("F2-Ⅰ", 27000),
                new LevelInfo("F2-Ⅱ", 39000),
                new LevelInfo("F2-Ⅲ", 51000),
                new LevelInfo("F3-Ⅰ", 63000),
                new LevelInfo("F3-Ⅱ", 78000),
                new LevelInfo("F3-Ⅲ", 93000),
                new LevelInfo("F4-Ⅰ", 108000),
                new LevelInfo("F4-Ⅱ", 126000),
                new LevelInfo("F4-Ⅲ", 144000),
                new LevelInfo("F5", 162000)
        ));

        levelInfoMap.put(JobType.B, Arrays.asList(
                new LevelInfo("B1", 0),
                new LevelInfo("B2", 24000),
                new LevelInfo("B3", 52000),
                new LevelInfo("B4", 78000),
                new LevelInfo("B5", 117000),
                new LevelInfo("B6", 169000)
        ));

        levelInfoMap.put(JobType.G, Arrays.asList(
                new LevelInfo("G1", 0),
                new LevelInfo("G2", 24000),
                new LevelInfo("G3", 52000),
                new LevelInfo("G4", 78000),
                new LevelInfo("G5", 117000),
                new LevelInfo("G6", 169000)
        ));

        levelInfoMap.put(JobType.T, Arrays.asList(
                new LevelInfo("T1", 0),
                new LevelInfo("T2", 24000),
                new LevelInfo("T3", 52000),
                new LevelInfo("T4", 78000),
                new LevelInfo("T5", 117000),
                new LevelInfo("T6", 169000)
        ));


    }

    public static void checkAndLevelUp(Member member) {
        List<LevelInfo> levels = levelInfoMap.get(member.getJobType());
        for (LevelInfo levelInfo : levels) {
            if (member.getTotalExp() >= levelInfo.getRequiredExp()) {
                member.updateLevel(levelInfo.getLevel());
            } else {
                break;
            }
        }
    }

    @Getter
    public static class LevelInfo {
        private final String level;
        private final long requiredExp;

        LevelInfo(String level, long requiredExp) {
            this.level = level;
            this.requiredExp = requiredExp;
        }

    }
}
