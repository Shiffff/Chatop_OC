package com.chatop.chatop.configuration;

import com.chatop.chatop.services.JWTService;
import com.chatop.chatop.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;


@Slf4j
@Service
public class JwtFilter  extends OncePerRequestFilter {

    private UserService userService;
    private JWTService jwtService;
    public JwtFilter(UserService userService, JWTService jwtService ) {
        this.userService = userService;
        this.jwtService = jwtService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            try {
                username = jwtService.extractUsername(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException  | IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Erreur de jeton JWT : " + e.getMessage());
                return;
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info(String.valueOf(SecurityContextHolder.getContext()));
        }
        filterChain.doFilter(request, response);
    }

}
