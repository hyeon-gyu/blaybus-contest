package contest.blaybus.v1.infrastructure.dto;

import lombok.Builder;

@Builder
public record ExpBarResponse(Long percent, Long totalExp) {
}
