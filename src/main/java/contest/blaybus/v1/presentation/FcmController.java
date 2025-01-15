package contest.blaybus.v1.presentation;


import contest.blaybus.v1.application.FirebaseCloudMessageService;
import contest.blaybus.v1.common.ApiResponse;
import contest.blaybus.v1.presentation.dto.NewFcmDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "PUSH 알림", description = "FCM 푸시알림 전송 API입니다.")
@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FirebaseCloudMessageService fcmService;

    @PostMapping("/test")
    public ApiResponse<String> test(
            @RequestBody NewFcmDTO dto) throws IOException {
        fcmService.sendMessageTo(dto);
        return ApiResponse.success("성공");
    }

    @GetMapping("/test2")
    public void test2() throws Exception {
        throw new Exception("테스트에러");
    }

}
