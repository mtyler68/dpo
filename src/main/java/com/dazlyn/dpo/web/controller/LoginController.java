package com.dazlyn.dpo.web.controller;

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

    public String login() {
        System.out.println("Logging in");
        log.info("action=login, user={}", credentials.getUserId());
        Identity.AuthenticationResult result = identity.login();
        if (identity.isLoggedIn()) {
            return "pretty:dashboard";
        } else {
            System.out.println("could not log in");
            return null;
        }
    }

    public String logout() {
        System.out.println("logout()");
        identity.logout();
        return "pretty:login";
    }
}
