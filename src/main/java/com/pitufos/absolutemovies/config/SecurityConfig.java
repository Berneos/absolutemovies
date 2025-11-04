package com.pitufos.absolutemovies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa proteção CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // permite tudo
            )
            .formLogin(login -> login.disable()) // desativa login padrão
            .httpBasic(basic -> basic.disable()); // desativa autenticação básica

        return http.build();
    }
}
