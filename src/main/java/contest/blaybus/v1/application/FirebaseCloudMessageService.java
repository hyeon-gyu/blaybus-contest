package contest.blaybus.v1.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.gson.JsonParseException;
import contest.blaybus.v1.application.dto.FcmMessage;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Notification;
import contest.blaybus.v1.domain.Post;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.domain.repository.NotificationRepository;
import contest.blaybus.v1.presentation.dto.NewFcmDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/blaybus-4d648/messages:send";
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    public void sendMessageTo(NewFcmDTO dto) throws IOException {
        String message = makeMessage(dto.fcmToken(), dto.title(), dto.content());

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
    }

    @Async
    public void sendPushNotificationsToMembers() {
        List<Member> memberList = memberRepository.findAllFcmToken();

        if (memberList.isEmpty()) {
            return; // 토큰이 없으면 푸시 알림 전송하지 않음
        }

        for (Member member : memberList) {
            try {
                sendMessageTo(NewFcmDTO.builder()
                        .fcmToken(member.getFcmToken())
                        .title("게시판")
                        .content("\uD83C\uDF89 새로운 글이 올라왔어요! \uD83D\uDCF0 확인해보세요!")
                        .build());
                final Notification newNotification = Notification.builder()
                        .title("게시판")
                        .content("\uD83C\uDF89 새로운 글이 올라왔어요! \uD83D\uDCF0 확인해보세요!")
                        .date(LocalDateTime.now())
                        .member(member)
                        .build();
                notificationRepository.save(newNotification);
            } catch (Exception e) {
                log.info("푸시알림 송신 실패 대상"+ member.getName());
            }
        }
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
