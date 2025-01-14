package contest.blaybus.v1.config;

import contest.blaybus.v1.security.handler.AuthAccessDeniedHandler;
import contest.blaybus.v1.security.handler.AuthEntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthEntryPointHandler authEntryPointHandler;
    private final AuthAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // HTTP 에서는 사용하지 않음 - CSRF 보호 비활성화
        http.csrf(csrf -> csrf.disable());

        http.httpBasic(HttpBasicConfigurer::disable);

        // Session 사용 안함
        http.sessionManagement(configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**", // Swagger UI 경로
                                "/v3/api-docs/**", // OpenAPI 경로
                                "/swagger-resources/**"
                        ).permitAll()
                        .requestMatchers("/auth/**").permitAll() // 로그인 및 회원가입 허용
                        .anyRequest().authenticated() // 그 외 요청 인증 필요
        );

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(authEntryPointHandler)
                        .accessDeniedHandler(accessDeniedHandler)
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
