package albert.mylocalrestaurant.authorizationserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // OAuth2 인증 서버의 설정을 처리하는 필터 체인 설정기를 생성합니다.
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        // OAuth2 인증 서버의 설정을 처리하는 필터 체인 설정기를 생성합니다.
        var authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        // OAuth2 인증 서버의 엔드포인트와 일치하는 요청만 필터링하도록 설정합니다.
        // 이를 통해 인증 서버의 엔드포인트에 대한 요청만 해당 필터가 처리하도록 합니다.
        return http
                // OAuth2 인증 서버의 엔드포인트에 매칭되는 요청만 처리합니다.
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(
                        // OAuth2 인증 서버의 설정을 처리하는 필터 체인 설정기를 적용합니다.
                        authorizationServerConfigurer,
                        // OpenID Connect 1.0을 활성화하여 ID 토큰 발급을 지원합니다.
                        authorizationServer -> authorizationServer.oidc(Customizer.withDefaults())
                )
                .build();
    }


    // 기본적인 보안 설정을 처리하는 필터 체인 설정기를 생성합니다.
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 모든 요청에 대해 인증을 요구합니다.
                // POST /api/users, POST /login 엔드포인트에 대해서는 인증을 요구하지 않습니다.
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/users", "/login").permitAll()
                        .anyRequest().authenticated())
                // CSRF 보호 기능을 비활성화합니다.
                .csrf(AbstractHttpConfigurer::disable)
                // 폼 로그인을 활성화합니다.
                .formLogin(Customizer.withDefaults())
                .build();
    }
}

