package contest.blaybus.v1.domain;

import lombok.Getter;

@Getter
public enum ExpType {

    PERFORMANCE_EVALUATION("인사평가", "pf"),
    JOB_QUEST("직무별 퀘스트", "job"),
    LEADER_QUEST("리더 부여 퀘스트", "ld"),
    COMPANY_PROJECT("전사 프로젝트", "co");

    private final String description;
    private final String category;

    ExpType(String description, String category) {
        this.description = description;
        this.category = category;
    }

    public static ExpType fromCode(String code) {

        for (ExpType type : ExpType.values()) {
            if (type.getCategory().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 카테고리입니다.");
    }
}
