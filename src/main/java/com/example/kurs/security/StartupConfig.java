package com.example.kurs.security;

import com.example.kurs.role.Role;
import com.example.kurs.role.RoleRepository;
import com.example.kurs.user.User;
import com.example.kurs.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StartupConfig {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;
    @Bean
    CommandLineRunner mainConfiguration() {
        return args -> {
//            Role r = roleRepository.getReferenceById(1L);
//            User user = new User();
//            user.setLogin("Admin");
//            user.setPassword(securityService.passwordEncoder().encode("Admin"));
//            user.addRole(r);
//            userRepository.save(user);
        };
    }
}
