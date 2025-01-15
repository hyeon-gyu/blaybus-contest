package contest.blaybus.v1.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    // personalId(Member) or identificationNumber(Admin)
    private String loginRequestId;
    private String password;
}