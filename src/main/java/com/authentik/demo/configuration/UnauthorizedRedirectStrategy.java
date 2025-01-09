package com.authentik.demo.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;

import java.io.IOException;

@RequiredArgsConstructor
public class UnauthorizedRedirectStrategy extends DefaultRedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
            final var redirectUrl = response.encodeRedirectURL(calculateRedirectUrl(request.getContextPath(), url));
            response.setHeader(HttpHeaders.LOCATION, redirectUrl);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().flush();
    }
}
