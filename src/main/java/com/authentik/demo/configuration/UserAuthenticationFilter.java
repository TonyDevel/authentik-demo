package com.authentik.demo.configuration;

import com.authentik.demo.configuration.properties.AuthentikConfigurationProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private static final String GROUPS_CLAIM = "groups";

    private final AuthentikConfigurationProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            if (authentication.getPrincipal() instanceof OidcUser oidcUser && !isUserMemberOfRequiredGroup(oidcUser)) {
                handleUnauthorizedUser(request);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isUserMemberOfRequiredGroup(OidcUser user) {
        final var groups = user.getClaimAsStringList(GROUPS_CLAIM);
        return groups != null && groups.contains(properties.getUserGroup().getName());
    }

    // User authorized in Authentik, but groups does not belong to the current application group
    private void handleUnauthorizedUser(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        invalidateSession(request);
        throw new AccessDeniedException("Access denied");
    }

    private void invalidateSession(HttpServletRequest request) {
        final var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
