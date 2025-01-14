package contest.blaybus.v1.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import contest.blaybus.v1.security.exception.NotOurUserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 인증에 실피했을때 (우리 사용자가 아닐 때)
 */

@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (authException.getCause() instanceof NotOurUserException exception) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(exception.getStatus());
            objectMapper.writeValue(response.getWriter(), "우리 사용자가 아닙니다.");
        }
    }

}
