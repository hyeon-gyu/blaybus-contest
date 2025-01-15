package contest.blaybus.v1.presentation;

import contest.blaybus.v1.application.LoginService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.infrastructure.dto.LoginResponseDTO;
import contest.blaybus.v1.presentation.dto.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 관련 API입니다.")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "Admin 로그인")
    @PostMapping("/admin/login")
    public ApiResponse<LoginResponseDTO> adminLogin(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO loginResponseDTO = loginService.adminLogin(request);
        return ApiResponse.success(loginResponseDTO);
    }

    @Operation(summary = "Member 로그인")
    @PostMapping("/member/login")
    public ApiResponse<LoginResponseDTO> memberLogin(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO loginResponseDTO = loginService.memberLogin(request);
        return ApiResponse.success(loginResponseDTO);
    }
}
