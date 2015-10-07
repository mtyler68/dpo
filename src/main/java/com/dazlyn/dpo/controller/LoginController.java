package com.dazlyn.dpo.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

@Named("loginController")
@RequestScoped
@Slf4j
public class LoginController {

    @Inject
    private Identity identity;

    @Inject
    private DefaultLoginCredentials credentials;

    @Inject
    @Named("realm")
    private Realm realm;

    public String login() {
        log.info("action=login, user={}, studio={} message=\"{}\"",
                credentials.getUserId(),
                realm.getName(),
                "authenticating user");

        Identity.AuthenticationResult result = identity.login();
        if (identity.isLoggedIn()) {
            return "pretty:dashboard";
        } else {
            log.info("action=login, user={}, studio={}, authResult={} message=\"{}\"",
                    credentials.getUserId(),
                    realm.getName(),
                    result,
                    "authentication failed");
            return null;
        }
    }

    public String logout() {
        log.info("action=logout, user={}, studio={}",
                ((User) identity.getAccount()).getLoginName(),
                realm.getName());

        identity.logout();
        return "pretty:login";
    }
}
