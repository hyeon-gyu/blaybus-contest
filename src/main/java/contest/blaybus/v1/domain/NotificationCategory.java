package contest.blaybus.v1.domain;

import lombok.Getter;

@Getter
public enum NotificationCategory {

    POST("게시글 관련 알림"),
    WQUEST("주별 퀘스트 관련 알림"),
    MQUEST("월별 퀘스트 관련 알림"),
    LEVELUP("레벨 업 관련 알림"),
    EXP("경험치 관련 알림");

    private final String description;

    NotificationCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
