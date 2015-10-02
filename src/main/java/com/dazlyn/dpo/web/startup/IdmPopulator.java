package com.dazlyn.dpo.web.startup;

import com.dazlyn.dpo.web.security.Studio;
import com.dazlyn.dpo.web.security.StudioRole;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;

@javax.ejb.Singleton
@javax.ejb.Startup
public class IdmPopulator {

    @Inject
    private PartitionManager partitionManager;

    @PostConstruct
    public void create() {

        // Make sure we are in development mode
        if (true) {

            IdentityManager idm = null;
            User user = null;

            // Dazlyn
            idm = createStudio("dazlyn", "Dazlyn");
            user = createUser(idm, "adam", "adam@example.com", "Adam", "Admin", StudioRole.ADMIN, StudioRole.EMPLOYEE);

            // CAD
            idm = createStudio("coronadodance", "Coronado Academy of Dance");
            user = createUser(idm, "india", "india@example.com", "India", "Instructor", StudioRole.INSTRUCTOR, StudioRole.EMPLOYEE);
        }
    }

    private IdentityManager createStudio(String name, String businessName) {
        Studio studio = new Studio(name);
        studio.setBusinessName(businessName);

        partitionManager.add(studio);
        IdentityManager idm = partitionManager.createIdentityManager(studio);

        idm.add(new Role(StudioRole.ADMIN.name()));
        idm.add(new Role(StudioRole.ASSISTANT.name()));
        idm.add(new Role(StudioRole.BOOKKEEPER.name()));
        idm.add(new Role(StudioRole.CLIENT.name()));
        idm.add(new Role(StudioRole.DIRECTOR.name()));
        idm.add(new Role(StudioRole.INSTRUCTOR.name()));
        idm.add(new Role(StudioRole.EMPLOYEE.name()));

        return idm;
    }

    private Role studioRole(IdentityManager idm, StudioRole role) {
        return BasicModel.getRole(idm, role.name());
    }

    private User createUser(IdentityManager idm, String username, String email, String first, String last, StudioRole... roles) {
        User user = new User(username);
        user.setEmail(email);
        user.setFirstName(first);
        user.setLastName(last);
        Password adminPwd = new Password(username);
        idm.add(user);
        idm.updateCredential(user, adminPwd);

        user = BasicModel.getUser(idm, username);
        RelationshipManager rm = partitionManager.createRelationshipManager();

        for (StudioRole studioRole : roles) {
            BasicModel.grantRole(rm, user, studioRole(idm, studioRole));
        }
        return user;
    }
}
