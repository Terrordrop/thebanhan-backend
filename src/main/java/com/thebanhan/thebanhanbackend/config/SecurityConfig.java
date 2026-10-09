package com.thebanhan.thebanhanbackend.config;

import com.thebanhan.thebanhanbackend.service.AdminUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminUserDetailsService adminUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .userDetailsService(adminUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/front/**", "/uploads/**").permitAll()
                        .requestMatchers("/api/admin/login").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/admin/sign-in.html")
                        .loginProcessingUrl("/api/admin/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/index.html", true)
                        .failureUrl("/admin/sign-in.html?error=1")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/admin/logout")
                        .logoutSuccessUrl("/admin/sign-in.html")
                )
                .exceptionHandling(exception -> exception
                        .defaultAuthenticationEntryPointFor(
                                (request, response, authException) -> response.sendError(401),
                                request -> request.getRequestURI().startsWith("/api/admin/")
                        )
                );

        return http.build();
    }
}
