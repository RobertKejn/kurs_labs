package com.example.kurs.security;

import com.example.kurs.role.Role;
import com.example.kurs.role.RoleRepository;
import com.example.kurs.user.User;
import com.example.kurs.user.UserDTO;
import com.example.kurs.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public Boolean checkAddUser(UserDTO userDTO) {
        if(!userRepository.findByLogin(userDTO.login()).isPresent()){
            User user = new User();
            user.setLogin(userDTO.login());
            user.setPassword(passwordEncoder().encode(userDTO.password()));
            Role role = roleRepository.findByName("ROLE_USER");
            user.addRole(role);
            userRepository.save(user);
            return true;
        }
        else return false;
    }
}
