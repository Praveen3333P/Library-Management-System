package com.cts.library.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.cts.library.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;

    public SecurityConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/member/**","/books/**","/admin/**", "/login","/fines/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
