package contest.blaybus.v1.application;


import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Notification;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.domain.repository.NotificationRepository;
import contest.blaybus.v1.infrastructure.dto.PushResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public List<PushResponse> getPushList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        List<Notification> notificationList = notificationRepository.findByMemberOrderByDateDesc(member);

        LocalDateTime now = LocalDateTime.now();

        return notificationList.stream()
                .map(notification -> {
                    String time;
                    long hoursDifference = java.time.Duration.between(notification.getDate().plusHours(9), now).toHours();

                    if (hoursDifference < 24) {
                        if(hoursDifference < 1) {time = "1 시간 이내";}
                        else {time = hoursDifference + "시간 전";}
                    } else {
                        time = notification.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
                    }

                    return PushResponse.builder()
                            .id(notification.getId())
                            .title(notification.getTitle())
                            .content(notification.getContent())
                            .time(time)
                            .category(notification.getCategory().toString())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
