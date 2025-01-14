package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.MemberService;
import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 정보", description = "멤버 정보 관련 API입니다.")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보 조회 API입니다")
    @GetMapping("/{memberId}")
    //  로그인 토큰 | 세션 | jwt 로 신원확인 ( 변경 사항 )
    public ApiResponse<MemberInfoResponse> getMemberInfo(
            @PathVariable(value = "memberId") Long memberId
    ) {
        return ApiResponse.success(memberService.getMemberInfo(memberId));
    }

    @Operation(summary = "마이페이지 - 비밀번호 변경 API", description = "마이페이지 비밀번호 변경 API입니다.")
    @PostMapping("/pwd")
    public ApiResponse<String> changePwd(
            @RequestBody NewPwdDTO dto) {
        return ApiResponse.success(memberService.changePwd(dto));
    }

    @Operation(summary = "마이페이지 - 기존 비밀번호와 중복 여부 조회 API", description = "마이페이지 비밀번호 중복 조회 API입니다. 중복이면 true, 중복이 아니라면 false입니다.")
    @PostMapping("/pwd/check")
    public ApiResponse<Boolean> checkDupPwd(
            @RequestBody NewPwdDTO dto) {
        return ApiResponse.success(memberService.checkDupPwd(dto));
    }
}
