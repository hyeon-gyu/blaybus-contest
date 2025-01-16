package contest.blaybus.v1.presentation.dto;

import contest.blaybus.v1.domain.NotificationCategory;
import lombok.Builder;

@Builder
public record NewFcmDTO(String fcmToken, String title, String content, NotificationCategory category) {
}
