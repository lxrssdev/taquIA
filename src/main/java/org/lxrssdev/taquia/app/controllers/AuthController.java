package org.lxrssdev.taquia.app.controllers;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class AuthController {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/dashboard", "/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/kitchen","/kitchen/**", "/waiter")
                .hasRole("KITCHEN")
                .anyRequest()
                .permitAll()
        ).formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccesHandler())
                .permitAll()
        ).logout(logout -> logout
                .logoutSuccessUrl("/menu")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccesHandler(){
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            boolean isKitchen = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_KITCHEN"));

            if(isAdmin){
                response.sendRedirect("/dashboard");
            }else if(isKitchen){
                response.sendRedirect("/kitchen");
            }else{
                response.sendRedirect("/menu");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
