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
//En una configuración de seguridad de Spring, se crea una clase que extiende WebSecurityConfigurerAdapter o se anota con @EnableWebSecurity
// para personalizar la configuración de seguridad para la aplicación web.
// Esta clase es típicamente anotada con @Configuration
@EnableWebSecurity
//es una anotación en Spring Security que se utiliza para
// habilitar la configuración de seguridad web en una aplicación Spring.
// Al agregar esta anotación a una configuración de clase (generalmente una
// clase que extiende WebSecurityConfigurerAdapter), se activa la configuración de
// seguridad web proporcionada por Spring Security.
public class SecurityConfig {
    @Bean //genera una instancia de este metodo dento del contexto de spring y lo ejecuta al comienzo de la aplicacion
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                //requesMatcher es un método en la configuración de seguridad de Spring Security
                // que se utiliza para definir las reglas de autorización basadas en las solicitudes HTTP. Permite especificar
                // a qué solicitudes se aplicarán ciertas configuraciones de seguridad.
                .requestMatchers("/web/login.html", "/web/assets/**", "/index.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login", "/api/clients").permitAll()
                .requestMatchers("/web/*", "/api/clients/**",
                        "/api/accounts/*/transactions").hasAuthority("CLIENT")

                .requestMatchers(HttpMethod.POST, "/api/clients/current/accounts",
                        "api/clients/current/cards", "/api/transactions").hasAuthority("CLIENT")

                .requestMatchers("/web/ADMIN/**", "/h2-console/**").hasAuthority("ADMIN")
                .anyRequest().denyAll()
        );

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //es un tipo de ataque de seguridad web que explota la confianza que un sitio web tiene en el navegador del usuario. En un ataque CSRF,
        // un atacante engaña al navegador del usuario para realizar acciones no deseadas en un
        // sitio web en el que el usuario está autenticado. ejmplo envio de una solicitud maliciosa, entre otros.

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
        //lo q va a hacer es verificar que
        //todo este bien y si algo sale mal, nos va a dar un error 403

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
