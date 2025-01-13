package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.AdminService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.domain.JobType;
import contest.blaybus.v1.domain.Team;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/")
    @Operation(summary = "신규 사원 생성", description = "어드민 신규사원 생성 API 입니다.")
    public ApiResponse<String> addMember (
            @RequestBody NewMemberDTO dto) throws ParseException {
        return ApiResponse.success(adminService.addMember(dto));
    }

    @GetMapping("/team")
    @Operation(summary = "소속 목록 조회", description = "어드민 신규사원 생성시 소속 목록 조회 API 입니다")
    public ApiResponse<List<String>> getTeamList() {
        return ApiResponse.success(adminService.getTeamList());
    }

    @GetMapping("/jobtype")
    @Operation(summary = "직군 목록 조회", description = "어드민 신규사원 생성시 직군 목록 조회 API 입니다")
    public ApiResponse<List<String>> getJobTypeList() {
        return ApiResponse.success(adminService.getJobTypeList());
    }
}
