package com.challenge_java.challenge_java.utils;

import com.challenge_java.challenge_java.model.services.UserDetailsImpl;
import com.challenge_java.challenge_java.request.LoginRequest;
import com.challenge_java.challenge_java.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class Utils {

    public Map<String, Object> getJwtCookie (
            AuthenticationManager authenticationManager,
            String username,
            String password,
            JwtUtils jwtUtils
    ) {
        Map<String, Object> obj = new HashMap<>();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        obj.put("userDetails", userDetails);
        obj.put("jwtCookie", jwtCookie);

        return  obj;
    }
}
