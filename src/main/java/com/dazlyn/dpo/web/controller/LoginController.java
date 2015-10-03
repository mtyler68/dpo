package com.dazlyn.dpo.web.controller;

import com.dazlyn.dpo.web.security.Studio;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;

@Named("loginController")
@RequestScoped
@Slf4j
public class LoginController {

    @Inject
    private Identity identity;

    @Inject
    private DefaultLoginCredentials credentials;

    @Inject
    @Named("studio")
    private Studio studio;

    public String login() {
        log.info("action=login, user={}, studio={} message=\"{}\"",
                credentials.getUserId(),
                studio.getName(),
                "authenticating user");

        Identity.AuthenticationResult result = identity.login();
        if (identity.isLoggedIn()) {
            return "pretty:dashboard";
        } else {
            log.info("action=login, user={}, studio={}, message=\"{}\"",
                    credentials.getUserId(),
                    studio.getName(),
                    "authentication failed");
            return null;
        }
    }

    public String logout() {
        log.info("action=logout, user={}, studio={}",
                credentials.getUserId(),
                studio.getName());

        identity.logout();
        return "pretty:login";
    }
}
