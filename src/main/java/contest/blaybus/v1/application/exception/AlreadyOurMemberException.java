package contest.blaybus.v1.application.exception;

public class AlreadyOurMemberException extends RuntimeException {

    public AlreadyOurMemberException(String personalId) {
        super(String.format("개인 ID '%s'를 가진 회원이 이미 존재합니다.", personalId));
    }
}
