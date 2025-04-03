package com.example.kurs.security;

import com.example.kurs.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(HttpMethod.POST, "/registration").permitAll()

                                .requestMatchers(HttpMethod.GET, "/projects/{projectId}/tasks/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/projects/{projectId}/tasks/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/projects/{projectId}/tasks/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/projects/{projectId}/tasks/**").hasAnyRole("USER", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/projects/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/projects/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/projects/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/projects/**").hasRole("ADMIN")


                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/projects")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(securityService.passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }
}

