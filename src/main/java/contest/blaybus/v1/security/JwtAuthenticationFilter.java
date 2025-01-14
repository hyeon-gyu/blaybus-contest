package contest.blaybus.v1.security;

import contest.blaybus.v1.security.handler.AuthEntryPointHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_PREFIX = "Bearer ";

    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthEntryPointHandler authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 1 요청이 들어옴.
        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && isBearer(authorizationHeader)) {
            try {
                String jwtToken = authorizationHeader.substring(JWT_TOKEN_PREFIX.length());
                jwtAuthenticationService.authenticate(jwtToken);
            } catch (Exception e) {
                authenticationEntryPoint.commence(
                        request, response, new AuthenticationException(e.getMessage(), e) {
                        });
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPublicUri(final String requestURI) {
        String[] publicUri = {
                "/swagger-ui/**", // Swagger UI 경로
                "/v3/api-docs/**", // OpenAPI 경로
                "/swagger-resources/**", // Swagger 리소스 경로
                "/auth/**"
        };
        return Arrays.stream(publicUri)
                .anyMatch(requestURI::startsWith);
    }

    private boolean isBearer(final String authorizationHeader) {
        return authorizationHeader.startsWith(JWT_TOKEN_PREFIX);
    }
}