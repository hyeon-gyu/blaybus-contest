package contest.blaybus.v1.application;


import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import contest.blaybus.v1.infrastructure.dto.LoginResponseDTO;
import contest.blaybus.v1.presentation.dto.LoginRequestDTO;
import contest.blaybus.v1.presentation.exception.LoginFailException;
import contest.blaybus.v1.security.TokenProviderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final TokenProviderService tokenProviderService;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Override
    public LoginResponseDTO adminLogin(LoginRequestDTO request) {
        Admin admin = adminRepository.findByIdentificationNumber(request.getIdentificationNumber())
                .orElseThrow(EntityNotFoundException::new);

        checkPassword(request.getPassword(), admin.getPassword());
        String jwtToken = tokenProviderService.create(request.getIdentificationNumber());

        return new LoginResponseDTO("Admin logged in successfully", admin.getRole(), jwtToken);
    }

    @Override
    public LoginResponseDTO memberLogin(LoginRequestDTO request) {
        Member member = memberRepository.findByIdentificationNumber(request.getIdentificationNumber())
                .orElseThrow(EntityNotFoundException::new);

        checkPassword(request.getPassword(), member.getPassword());
        String jwtToken = tokenProviderService.create(request.getIdentificationNumber());

        return new LoginResponseDTO("Admin logged in successfully", member.getRole(), jwtToken);
    }

    private void checkPassword(String requestPassword, String password) {
        String encodePassword = passwordEncoder.encode(requestPassword);
        boolean isMatch = passwordEncoder.matches(encodePassword, password);
        if (!isMatch) {
            throw new LoginFailException();
        }
    }
}