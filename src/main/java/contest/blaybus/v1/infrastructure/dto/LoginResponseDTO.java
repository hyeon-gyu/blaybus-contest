package contest.blaybus.v1.infrastructure.dto;

import contest.blaybus.v1.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private Role role;
    private String jwtToken;
}
