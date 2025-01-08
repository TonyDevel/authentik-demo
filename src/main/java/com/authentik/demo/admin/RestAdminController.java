package com.authentik.demo.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class RestAdminController {

    @GetMapping("/current/email")
    public String getCurrentUserEmail(@AuthenticationPrincipal OidcUser user) {
        return user.getEmail();
    }
}
