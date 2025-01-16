package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.ImageService;
import contest.blaybus.v1.application.MemberService;
import contest.blaybus.v1.infrastructure.dto.MemberInfoResponse;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.presentation.dto.CheckPwdDTO;
import contest.blaybus.v1.presentation.dto.NewProfileImageDTO;
import contest.blaybus.v1.presentation.dto.NewPwdDTO;
import contest.blaybus.v1.presentation.dto.NewUuidDTO;
import contest.blaybus.v1.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "멤버 정보", description = "멤버 정보 관련 API입니다.")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ImageService imageService;

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보 조회 API입니다")
    @GetMapping
    public ApiResponse<MemberInfoResponse> getMemberInfo() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(memberService.getMemberInfo(currentMemberId));
    }

    @Operation(summary = "마이페이지 - 비밀번호 변경 API", description = "마이페이지 비밀번호 변경 API입니다.")
    @PostMapping("/pwd")
    public ApiResponse<String> changePwd(@RequestBody NewPwdDTO dto) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(memberService.changePwd(currentMemberId, dto));
    }

    @Operation(summary = "마이페이지 - 기존 비밀번호와 중복 여부 조회 API", description = "마이페이지 비밀번호 중복 조회 API입니다. 중복이면 true, 중복이 아니라면 false입니다.")
    @PostMapping("/pwd/check")
    public ApiResponse<Boolean> checkDupPwd(@RequestBody CheckPwdDTO dto) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(memberService.checkDupPwd(currentMemberId, dto));
    }

    @Operation(summary = "프로필 이미지 목록 조회 API", description = "구성원 정보 설정 - 프로필 이미지 목록 조회 API입니다.")
    @GetMapping("/profile")
    public ApiResponse<List<String>> getProfileImgList(){
        return ApiResponse.success(imageService.getProfileImageUrls());
    }

    @Operation(summary = "프로필 이미지 적용 API", description = "구성원 정보 설정 - 프로필 이미지 선택 API입니다.")
    @PostMapping("/profile")
    public ApiResponse<String> updateProfileImg(@RequestBody NewProfileImageDTO dto) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(memberService.updateProfileImg(currentMemberId, dto));
    }

    @Operation(summary = "멤버별 fcm token 업데이트", description = "FCM 푸시 알림을 위해서 앱 이용자의 기기 고유값을 저장하고 있어야합니다.")
    @PostMapping("/uuid")
    public ApiResponse<String> updateUuid(@RequestBody NewUuidDTO dto) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return ApiResponse.success(memberService.updateFcmToken(currentMemberId, dto));
    }
}
