package com.akshai.Ecommerce.api.Security;

import com.akshai.Ecommerce.Models.LocalUser;
import com.akshai.Ecommerce.Repositories.LocalUserRepository;
import com.akshai.Ecommerce.Service.JWTService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    LocalUserRepository localUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader("Authorization");
        if(jwt != null && jwt.startsWith("Bearer ")){
            jwt=jwt.substring(7);
            try {
                String username = jwtService.getUserName(jwt);
                Optional<LocalUser> opUser=localUserRepository.findByUsernameIgnoreCase(username);
                if(opUser.isPresent()){
                    LocalUser user=opUser.get();
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (JWTDecodeException e){

            }

        }

        filterChain.doFilter(request,response);
    }
}
