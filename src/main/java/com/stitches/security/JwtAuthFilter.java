package com.stitches.security;

import com.stitches.service.serviceImpl.UserInfoUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;


    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    public JwtAuthFilter(HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String authHeader = request.getHeader("Authorization");
         String token = null;
         String username = null;
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }else {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                token = authHeader.substring(7);
//                username = jwtService.extractUsername(token);
//            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("========>>>>>>>>>>>>> we are here");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println(userDetails);
                log.info("user details : {}", userDetails);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }else {
                System.out.println(username);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
                System.out.println("============>>>>>>>>>>> did not enter");
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | SignatureException | AccessDeniedException | BadCredentialsException |
                 MethodNotAllowedException | NoHandlerFoundException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}

