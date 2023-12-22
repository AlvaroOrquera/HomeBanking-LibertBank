package com.mindhub.homebanking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/web/login.html","/web/assets/**","/index.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                .requestMatchers("/web/*","/api/clients/**","/api/accounts/*/transactions").hasAuthority("CLIENT")
                .requestMatchers(HttpMethod.GET, "/api/clients/current").hasAuthority("CLIENT")
                .requestMatchers("/web/ADMIN/**","/h2-console/**").hasAuthority("ADMIN")
                .anyRequest().denyAll()
        );

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                frameOptionsConfig -> frameOptionsConfig.disable()));

        http.formLogin(formLogin -> formLogin
                .loginPage("/index.html")
                .loginProcessingUrl("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> clearAuthenticationAttributes(request))
                .failureHandler((request, response, exception) -> response.sendError(401))
                .permitAll());

        http.exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> response.sendError(403)));

        http.logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"));

        http.rememberMe(Customizer.withDefaults());


        return http.build();

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }

}
