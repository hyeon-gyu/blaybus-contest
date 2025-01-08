package contest.blaybus.v1.domain;

import lombok.Getter;

public enum Team {
    EUMSEONG1("음성 1센터"),
    EUMSEONG2("음성 2센터"),
    YONGIN_BAEKAM("용인백암센터"),
    NAMYANGJU("남양주센터"),
    PAJU("파주센터"),
    BUSINESS_PLANNING("사업기획팀"),
    GROSS("그로스팀"),
    CX("CX팀");

    private final String displayName;

    Team(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

