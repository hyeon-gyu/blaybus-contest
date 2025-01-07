package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.AdminService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.presentation.dto.NewMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/")
    public ApiResponse<String> addMember(
            @RequestBody NewMemberDTO dto) {
        return ApiResponse.success(adminService.addMember(dto));
    }
}
