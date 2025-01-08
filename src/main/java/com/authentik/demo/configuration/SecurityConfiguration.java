package com.authentik.demo.configuration;

import com.authentik.demo.configuration.properties.AuthentikConfigurationProperties;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final RequestMatcher ADMIN_REQUEST_MATCHER = new AntPathRequestMatcher("/admin/**");

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthentikConfigurationProperties properties,
                                           UserAuthenticationFilter authenticationFilter) {
        return http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(
                exceptionConfig -> exceptionConfig
                    .authenticationEntryPoint(
                        // in a case of security exception send user to login page
                        new LoginUrlAuthenticationEntryPoint(properties.getUserGroup().getAuthorizationEntryPoint())
                    )
            )
            .authorizeHttpRequests(request -> request
                .requestMatchers(ADMIN_REQUEST_MATCHER)
                .authenticated()
                .anyRequest()
                .permitAll()
            )
            .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class)
            .oauth2Login(Customizer.withDefaults())
            .build();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        return new RestClientAuthorizationCodeTokenResponseClient();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public OAuth2LoginAuthenticationProvider oAuth2LoginAuthenticationProvider() {
        return new OAuth2LoginAuthenticationProvider(accessTokenResponseClient(), oauth2UserService());
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public OidcAuthorizationCodeAuthenticationProvider oidcAuthorizationCodeAuthenticationProvider() {
        return new OidcAuthorizationCodeAuthenticationProvider(
            accessTokenResponseClient(),
            oidcUserService()
        );
    }

}
