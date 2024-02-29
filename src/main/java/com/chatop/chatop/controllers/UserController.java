package com.chatop.chatop.controllers;


import com.chatop.chatop.dto.AuthentificationDTO;
import com.chatop.chatop.dto.ErrorEntity;
import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.services.JWTService;
import com.chatop.chatop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "auth")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTService jwtService;

    @PostMapping(path = "auth/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object register(@RequestBody User user) {
            this.userService.register(user);
            return this.jwtService.generate(user.getEmail());

    }
    @PostMapping(path = "auth/login")
    public Map<String, String> login(@RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password())
        );
            return this.jwtService.generate(authentificationDTO.email());

    }

    @GetMapping(path = "auth/me")
    public ResponseEntity<?> me() {
        User UserInfo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(new UserDTO(UserInfo));
    }

    @GetMapping(path = "user/{id}")
    public UserDTO userById(@PathVariable Integer id) {
        User UserInfo = (User) userService.findUserById(id);;
        return new UserDTO(UserInfo);
    }
}
