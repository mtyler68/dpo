package com.dazlyn.dpo.security;

import javax.enterprise.event.Observes;
import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent evt) {
        SecurityConfigurationBuilder builder = evt.getBuilder();
//        builder
//                .http()
//                .forPath("/back/*")
//                .authorizeWith()
//                .expression("#{authorizationChecker.hasAnyRealmRole(['ADMIN','INSTRUCTOR'])}")
//                .redirectTo("/access-denied.xhtml").whenForbidden()
//                .http()
//                .forPath("/back/admin/*")
//                .authorizeWith()
//                .role(RealmRole.ADMIN.name())
//                .redirectTo("/access-denied.xhtml").whenForbidden();
    }
}
