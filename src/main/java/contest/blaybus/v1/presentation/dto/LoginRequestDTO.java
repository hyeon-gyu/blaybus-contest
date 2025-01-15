package contest.blaybus.v1.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDTO {
    private String identificationNumber;
    private String password;
}