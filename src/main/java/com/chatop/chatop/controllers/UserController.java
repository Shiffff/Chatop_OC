package com.chatop.chatop.controllers;


import com.chatop.chatop.dto.AuthentificationDTO;
import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.services.JWTService;
import com.chatop.chatop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    public Map<String, String> register(@RequestBody User user) {
        log.info("inscription");
        this.userService.register(user);
        return this.jwtService.generate(user.getEmail());

    }



    @PostMapping(path = "auth/login")
    public Map<String, String> login(@RequestBody AuthentificationDTO authentificationDTO){
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.email(), authentificationDTO.password())
        );
        if(authenticate.isAuthenticated()){
            return this.jwtService.generate(authentificationDTO.email());
        }
        log.info("resultat {}", authenticate.isAuthenticated());
        return null;
    }

    @GetMapping(path = "auth/me")
    public UserDTO me() {
        User UserInfo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO(UserInfo);
    }

    @GetMapping(path = "user/{id}")
    public UserDTO userById(@PathVariable Integer id) {
        User UserInfo = (User) userService.findUserById(id);;
        return new UserDTO(UserInfo);
    }

}
