package com.example.kurs.security;

import com.example.kurs.user.User;
import com.example.kurs.user.UserDTO;
import com.example.kurs.user.UserRepository;
import com.example.kurs.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
public class SecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity registerNewUser(@RequestBody UserDTO userDTO) {
        if(securityService.checkAddUser(userDTO)) return ResponseEntity.created(URI.create("/login")).build();
        return ResponseEntity.badRequest().build();
    }
}