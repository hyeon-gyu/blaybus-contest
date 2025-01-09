package contest.blaybus.v1.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum JobType {
    F("현장직군", "F1-Ⅰ", 0),
    B("관리직군", "B1", 0),
    G("성장전략", "G1", 0),
    T("기술직군", "T1", 0);

    private final String description;
    private final String startLevel;
    private final long startExp;

    JobType(String description, String startLevel, long startExp) {
        this.description = description;
        this.startLevel = startLevel;
        this.startExp = startExp;
    }

    @JsonCreator
    public static JobType parsing(String inputValue) {
        return Stream.of(JobType.values())
                .filter(jobType -> jobType.toString().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("올바른 직군을 입력하세요"));
    }


}
