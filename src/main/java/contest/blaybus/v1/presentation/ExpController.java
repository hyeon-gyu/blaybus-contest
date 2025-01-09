package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.ExpService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "경험치", description = "경험치 조회 관련 API 입니다.")
@RestController
@RequestMapping("/exp")
@RequiredArgsConstructor
public class ExpController {

    private final ExpService expService;

    @Operation(summary = "경험치 현황 조회", description = "현재 레벨, 총 누적 경험치, 작년까지 누적된 경험치, 올해 획득한 경험치, 다음 레벨 달성에 필요한 총 경험치, 잔여 경험치")
    @GetMapping("/{memberId}") // 사용자 인증 방식 변경 예정
    public ApiResponse<ExpStatusResponse> getExp(@PathVariable(value = "memberId") Long memberId) {
        return ApiResponse.success(expService.getExpStatus(memberId));
    }

}
