package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.QuestServiceImpl;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.QuestMonthResponse;
import contest.blaybus.v1.infrastructure.dto.QuestWeekResponse;
import contest.blaybus.v1.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestServiceImpl questServiceImpl;

    @GetMapping("/week")
    public ApiResponse<List<QuestWeekResponse>> getMyQuestionWeek() {
        Long memberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(questServiceImpl.getMyQuestWeek(memberId));
    }

    @GetMapping("/month")
    public ApiResponse<List<QuestMonthResponse>> getMyQuestionMonth() {
        Long memberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(questServiceImpl.getMyQuestMonth(memberId));
    }

}
