package com.board.project.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.warn("Success Handler");



        List<String> roleNames = new ArrayList<>();

        authentication.getAuthorities().forEach(authority -> {
            roleNames.add(authority.getAuthority());
        });

        authentication.getAuthorities().forEach(authority -> {
            log.info("Success Authentication : " + authority.getAuthority());
        });

        response.sendRedirect("/board/boardDetail");
    }
}
