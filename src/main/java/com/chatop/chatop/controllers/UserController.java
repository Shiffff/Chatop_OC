package com.chatop.chatop.controllers;


import com.chatop.chatop.dto.AuthentificationDTO;
import com.chatop.chatop.dto.UserDTO;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.schemaResApi.loginSchema;
import com.chatop.chatop.schemaResApi.messageSchema;
import com.chatop.chatop.schemaResApi.registerSchema;
import com.chatop.chatop.services.JWTService;
import com.chatop.chatop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Données de connexion",
            required = true,
            content = @Content(schema = @Schema(implementation = registerSchema.class))
    ),
            responses = {

                    @ApiResponse(
                            description = "sucess",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = loginSchema.class))
                    ),
                    @ApiResponse(
                            description = "Utilisateur non authentifié",
                            responseCode = "403",
                            content = @Content(schema = @Schema(hidden = true))
                    )

            }


    )
    @PostMapping(path = "auth/register", consumes = MediaType.APPLICATION_JSON_VALUE)

    public Map<String, String> register(@RequestBody User user) {
        log.info("inscription");
        this.userService.register(user);
        return this.jwtService.generate(user.getEmail());

    }
    @Operation(
            responses = {
                    @ApiResponse(
                            description = "sucess",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = loginSchema.class))
                    ),
                    @ApiResponse(
                            description = "Utilisateur non authentifié",
                            responseCode = "403",
                            content = @Content(schema = @Schema(hidden = true))
                    ),


            }


    )


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
    @Operation(
            responses = {
                    @ApiResponse(
                            description = "sucess",
                            responseCode = "200"

                    ),
                    @ApiResponse(
                            description = "Utilisateur non authentifié",
                            responseCode = "403",
                            content = @Content(schema = @Schema(hidden = true))
                    )

            }


    )
    @GetMapping(path = "auth/me")
    public UserDTO me() {
        User UserInfo = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO(UserInfo);
    }

    @Operation(
            responses = {
                    @ApiResponse(
                            description = "sucess",
                            responseCode = "200"

                    ),
                    @ApiResponse(
                            description = "Utilisateur non authentifié",
                            responseCode = "403",
                            content = @Content(schema = @Schema(hidden = true))
                    )

            }


    )

    @GetMapping(path = "user/{id}")
    public UserDTO userById(@PathVariable Integer id) {
        User UserInfo = (User) userService.findUserById(id);;
        return new UserDTO(UserInfo);
    }


}
