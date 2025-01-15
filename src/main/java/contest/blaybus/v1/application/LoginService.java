package contest.blaybus.v1.application;

import contest.blaybus.v1.infrastructure.dto.LoginResponseDTO;
import contest.blaybus.v1.presentation.dto.LoginRequestDTO;

public interface LoginService {
    LoginResponseDTO adminLogin(LoginRequestDTO request);

    LoginResponseDTO memberLogin(LoginRequestDTO request);
}
