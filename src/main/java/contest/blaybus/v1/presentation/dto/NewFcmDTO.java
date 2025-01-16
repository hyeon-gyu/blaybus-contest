package contest.blaybus.v1.presentation.dto;

import lombok.Builder;

@Builder
public record NewFcmDTO(String fcmToken, String title, String content) {
}
