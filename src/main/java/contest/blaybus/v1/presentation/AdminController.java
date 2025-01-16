package contest.blaybus.v1.presentation;


import com.google.protobuf.Api;
import contest.blaybus.v1.application.AdminService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.presentation.dto.AdminCheckDupPwdDTO;
import contest.blaybus.v1.presentation.dto.CheckPwdDTO;
import contest.blaybus.v1.presentation.dto.ModifyDateDTO;
import contest.blaybus.v1.presentation.dto.ModifyJobTypeDTO;
import contest.blaybus.v1.presentation.dto.ModifyNameDTO;
import contest.blaybus.v1.presentation.dto.ModifyNumberDTO;
import contest.blaybus.v1.presentation.dto.ModifyPwdDTO;
import contest.blaybus.v1.presentation.dto.ModifyTeamDTO;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "어드민(관리자) ", description = "어드민 제어 관련 API입니다.")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @Operation(summary = "신규 사원 생성", description = "어드민 신규사원 생성 API 입니다. 입사일은 2024-01-01 형식으로 보내야합니다.")
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
    @Operation(summary = "직군 목록 조회", description = "어드민 신규사원 생성시 직군 목록 조회 API 입니다.")
    public ApiResponse<List<String>> getJobTypeList() {
        return ApiResponse.success(adminService.getJobTypeList());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "중복 아이디 여부 조회", description = "어드민 신규사원 생성시 중복 아이디 여부 조회 API 입니다. 중복 ID가 있을시 false 리턴")
    public ApiResponse<Boolean> checkDupId(
            @PathVariable(value = "id") String id) {
        return ApiResponse.success(adminService.checkDupId(id));
    }

    @PostMapping("/pwd")
    @Operation(summary = "비밀번호 룰 통과 여부 조회(영문,숫자 포함 8자 이상)", description = "어드민 신규사원 생성시 비밀번호 룰 통과 여부 조회 API입니다. 통과시 true 리턴")
    public ApiResponse<Boolean> checkPwdRule(
            @RequestBody CheckPwdDTO dto) {
        return ApiResponse.success(adminService.checkPwdRule(dto));
    }

    @GetMapping("/search/team/{team}")
    @Operation(summary = "구성원 검색 - 소속으로 검색하기", description = "소속으로 사원 목록리스트 조회하는 API 입니다.")
    public ApiResponse<List<MemberInfoResponse>> searchByTeam(
            @PathVariable(value = "team") String team) {
        return ApiResponse.success(adminService.searchByTeam(team));
    }

    @GetMapping("/search/{keyword}")
    @Operation(summary = "구성원 검색 - 이름 혹은 사번으로 검색하기", description = "이름이나 사번으로 사원 목록리스트 조회하는 API 입니다. 사번은 10자리 숫자가 아니면 조회가 안됩니다!")
    public ApiResponse<List<MemberInfoResponse>> searchByNameOrNumber (
            @PathVariable(value = "keyword") String keyword) {
        return ApiResponse.success(adminService.searchByNameOrNumber(keyword));
    }

    @PostMapping("/mod/name")
    @Operation(summary = "구성원 정보 설정 - 이름 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyName(
            @RequestBody ModifyNameDTO dto) {
        return ApiResponse.success(adminService.modifyName(dto));
    }

    @PostMapping("/mod/team")
    @Operation(summary = "구성원 정보 설정 - 소속 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyTeam(
            @RequestBody ModifyTeamDTO dto) {
        return ApiResponse.success(adminService.modifyTeam(dto));
    }

    @PostMapping("/mod/num")
    @Operation(summary = "구성원 정보 설정 - 사번 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyNumber(
            @RequestBody ModifyNumberDTO dto) {
        return ApiResponse.success(adminService.modifyNumber(dto));
    }

    @PostMapping("/mod/jobType")
    @Operation(summary = "구성원 정보 설정 - 직군 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyJobType(
            @RequestBody ModifyJobTypeDTO dto) {
        return ApiResponse.success(adminService.modifyJobType(dto));
    }

    @PostMapping("/mod/password")
    @Operation(summary = "구성원 정보 설정 - 비밀번호 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyPwd(
            @RequestBody ModifyPwdDTO dto) {
        return ApiResponse.success(adminService.modifyPwd(dto));
    }

    @PostMapping("/mod/date")
    @Operation(summary = "구성원 정보 설정 - 입사일 변경", description = "기존과 동일한 값이 요청되면 false로 응답합니다! 변경 성공시 true")
    public ApiResponse<Boolean> modifyDate(
            @RequestBody ModifyDateDTO dto) throws ParseException {
        return ApiResponse.success(adminService.modifyDate(dto));
    }

    @PostMapping("/pwd/check")
    @Operation(summary = "구성원 정보 설정 - 기존 비밀번호와 중복 여부 조회 API", description = "중복이면 true, 중복이 아니라면 false입니다.")
    public ApiResponse<Boolean> checkPwd(
            @RequestBody AdminCheckDupPwdDTO dto) {
        return ApiResponse.success(adminService.checkDupPwd(dto));
    }
}
