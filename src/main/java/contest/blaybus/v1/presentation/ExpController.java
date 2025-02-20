package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.ExpService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.ExpBarResponse;
import contest.blaybus.v1.infrastructure.dto.ExpHistoryResponse;
import contest.blaybus.v1.infrastructure.dto.ExpStatusResponse;
import contest.blaybus.v1.infrastructure.dto.RecentExpInfoResponse;
import contest.blaybus.v1.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "경험치", description = "경험치 조회 관련 API 입니다.")
@RestController
@RequestMapping("/exp")
@RequiredArgsConstructor
public class ExpController {

    private final ExpService expService;

    @Operation(summary = "경험치 현황 조회", description = "현재 레벨, 총 누적 경험치, 작년까지 누적된 경험치, 올해 획득한 경험치, 다음 레벨 달성에 필요한 총 경험치, 잔여 경험치")
    @GetMapping
    public ApiResponse<ExpStatusResponse> getExp() throws IOException {
        Long getCurrentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(expService.getExpStatus(getCurrentMemberId));
    }

    @Operation(summary = "최근 획득한 경험치 조회", description = "최근 수령한 경험치 날짜, 내용, 숫자 표시")
    @GetMapping("/recent")
    public ApiResponse<RecentExpInfoResponse> getRecentExpInfo() {
        Long getCurrentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(expService.getRecentExpInfo(getCurrentMemberId));
    }

    @Operation(summary = "경험치 Bar (작년까지 누적)", description = "작년까지 누적된 경험치 / 해당 레벨의 총 필요 경험치")
    @GetMapping("/bar")
    public ApiResponse<ExpBarResponse> getExpBar() {
        Long getCurrentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(expService.getExpBar(getCurrentMemberId));
    }

    @Operation(summary = "경험치 Bar (올해 누적)", description = "올해 내에 누적된 경험치 / 1년에 개인이 받을 수 있는 중위평균 경험치")
    @GetMapping("/bar/today")
    public ApiResponse<ExpBarResponse> getExpBarThisYear() {
        Long getCurrentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(expService.getExpBarThisYear(getCurrentMemberId));
    }

    @Operation(summary = "경험치 내역 카테고리별, 시간별 조회", description = "category 목록 : all(전체), pf(인사평가), job(직무), ld(리더), co(전사) & order 목록 : desc(최신순), asc(오래된순)")
    @GetMapping("/list")
    public ApiResponse<List<ExpHistoryResponse>> getExpHistoryPerCategory(
            @RequestParam(value = "category") String category,
            @RequestParam(value = "order") String order) {
        Long getCurrentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(expService.getExpHistoryPerCategory(getCurrentMemberId, category, order));
    }


}
