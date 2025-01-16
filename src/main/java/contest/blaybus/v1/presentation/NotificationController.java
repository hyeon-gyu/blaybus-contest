package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.NotificationService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.PushResponse;
import contest.blaybus.v1.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "푸시알림", description = "푸시 알림 정보 관련 API입니다.")
@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/list")
    @Operation(summary = "푸시알림 수신 목록 조회 API 입니다.")
    public ApiResponse<List<PushResponse>> getPushList(){
        Long memberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(notificationService.getPushList(memberId));
    }
}
