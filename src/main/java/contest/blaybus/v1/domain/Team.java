package contest.blaybus.v1.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
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

    @JsonCreator
    public static Team parsing(String inputValue) {
        return Stream.of(Team.values())
                .filter(team -> team.getDisplayName().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("올바른 팀명을 입력하세요"));
    }

}

