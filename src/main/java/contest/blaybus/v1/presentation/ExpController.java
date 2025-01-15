package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.ExpService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.ExpHistoryResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "최근 획득한 경험치 조회", description = "최근 수령한 경험치 날짜, 내용, 숫자 표시")
    @GetMapping("/recent/{memberId}")
    public ApiResponse<RecentExpInfoResponse> getRecentExpInfo(@PathVariable(value = "memberId") Long memberId) {
        return ApiResponse.success(expService.getRecentExpInfo(memberId));
    }

    @Operation(summary = "경험치 Bar (작년까지 누적)", description = "작년까지 누적된 경험치 / 해당 레벨의 총 필요 경험치")
    @GetMapping("/bar/{memberId}")
    public ApiResponse<Long> getExpBar(
            @PathVariable(value = "memberId") Long memberId) {
        return ApiResponse.success(expService.getExpBar(memberId));
    }

    @Operation(summary = "경험치 Bar (올해 누적)", description = "올해 내에 누적된 경험치 / 1년에 개인이 받을 수 있는 중위평균 경험치")
    @GetMapping("/bar/today/{memberId}")
    public ApiResponse<Long> getExpBarThisYear(
            @PathVariable(value = "memberId") Long memberId) {
        return ApiResponse.success(expService.getExpBarThisYear(memberId));
    }

    @Operation(summary = "경험치 내역 카테고리별, 시간별 조회", description = "category 목록 : all(전체), pf(인사평가), job(직무), ld(리더), co(전사) & order 목록 : desc(최신순), asc(오래된순)")
    @GetMapping("/list/{memberId}")
    public ApiResponse<List<ExpHistoryResponse>> getExpHistoryPerCategory(
            @PathVariable(value = "memberId") Long memberId,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "order") String order) {
        return ApiResponse.success(expService.getExpHistoryPerCategory(memberId, category, order));
    }


}
