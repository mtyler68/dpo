package com.dazlyn.dpo.web.security;

import com.dazlyn.dpo.web.model.StudioRole;
import javax.enterprise.event.Observes;
import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent evt) {
        SecurityConfigurationBuilder builder = evt.getBuilder();
        builder
                //.http()
                //.forPath("/studios/{identity.account.partition.name}/back/*")
                //.authorizeWith()
                //.expression("#{identity.account.partition.name}")
                //.redirectTo("/access-denied.xhtml").whenForbidden()
                .http()
                .forPath("/back/*")
                .authorizeWith()
                .role(StudioRole.STUDIO_EMPLOYEE.name())
                .redirectTo("/access-denied.xhtml").whenForbidden()
                .http()
                .forPath("/back/admin/*")
                .authorizeWith()
                .role(StudioRole.ADMIN.name())
                .redirectTo("/access-denied.xhtml").whenForbidden();
    }
}
