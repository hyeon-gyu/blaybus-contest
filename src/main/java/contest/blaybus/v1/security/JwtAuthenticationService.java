package contest.blaybus.v1.security;

import contest.blaybus.v1.domain.Admin;
import contest.blaybus.v1.domain.Member;
import contest.blaybus.v1.domain.Role;
import contest.blaybus.v1.domain.repository.AdminRepository;
import contest.blaybus.v1.domain.repository.MemberRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final TokenProviderService tokenProviderService;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public void authenticate(final String jwtToken) {
        if (tokenProviderService.validate(jwtToken)) {

            // JWT 토큰으로 Key 추출
            String uniqueKey = tokenProviderService.extract(jwtToken);
            Optional<Member> memberOptional = memberRepository.findByPersonalId(uniqueKey);

            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                List<GrantedAuthority> authorities = createRole(member.getRole());
                UsernamePasswordAuthenticationToken authentication = createSpringToken(member, jwtToken, authorities);
                setSecurityContext(authentication);
                return;
            }

            Optional<Admin> adminOptional = adminRepository.findByIdentificationNumber(uniqueKey);
            if (adminOptional.isPresent()) {
                Admin admin = adminOptional.get();
                List<GrantedAuthority> authorities = createRole(admin.getRole());
                UsernamePasswordAuthenticationToken authentication = createSpringToken(admin, jwtToken, authorities);
                setSecurityContext(authentication);
            }
        }
    }

    // 우리의 Role을 Spring에 맞게 변환
    private List<GrantedAuthority> createRole(Role role) {
        // [USER_TYPE]
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    private UsernamePasswordAuthenticationToken createSpringToken(Object principal, String jwtToken, List<GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(principal, jwtToken, authorities);
    }

    private void setSecurityContext(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
