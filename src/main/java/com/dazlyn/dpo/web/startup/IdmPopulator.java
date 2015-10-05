package com.dazlyn.dpo.web.startup;

import com.dazlyn.dpo.web.model.GroupClass;
import com.dazlyn.dpo.web.security.Studio;
import com.dazlyn.dpo.web.model.StudioRole;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.Condition;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

@javax.ejb.Singleton
@javax.ejb.Startup
public class IdmPopulator {

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private RelationshipManager relationshipManager;

    @PostConstruct
    public void create() {

        // Make sure we are in development mode
        if (true) {

            IdentityManager idm = null;
            User user = null;

            // Dazlyn
            idm = createStudio("dazlyn", "Dazlyn");
            user = createUser(idm, "adam", "adam@example.com", "Adam", "Admin", StudioRole.ADMIN, StudioRole.STUDIO_EMPLOYEE);

            // CAD
            idm = createStudio("coronadodance", "Coronado Academy of Dance");
            User india = createUser(idm, "india", "india@example.com", "India", "Instructor", StudioRole.STUDIO_INSTRUCTOR, StudioRole.STUDIO_EMPLOYEE);

            GroupClass ballet5 = createGroupClass(idm, "Ballet 5", india);
            GroupClass contemp2 = createGroupClass(idm, "Contemporary 2", india);
        }
    }

    private IdentityManager createStudio(String name, String businessName) {
        Studio studio = new Studio(name);
        studio.setBusinessName(businessName);

        partitionManager.add(studio);
        IdentityManager idm = partitionManager.createIdentityManager(studio);

        idm.add(new Role(StudioRole.ADMIN.name()));
        idm.add(new Role(StudioRole.STUDIO_ASSISTANT.name()));
        idm.add(new Role(StudioRole.STUDIO_BOOKKEEPER.name()));
        idm.add(new Role(StudioRole.STUDIO_CLIENT.name()));
        idm.add(new Role(StudioRole.STUDIO_DIRECTOR.name()));
        idm.add(new Role(StudioRole.STUDIO_INSTRUCTOR.name()));
        idm.add(new Role(StudioRole.STUDIO_EMPLOYEE.name()));

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

    private GroupClass createGroupClass(IdentityManager idm, String title, User instructor) {
        GroupClass group = new GroupClass(UUID.randomUUID().toString());
        group.setTitle(title);
        idm.add(group);

        group = getGroupClass(idm, group.getName());
        BasicModel.addToGroup(relationshipManager, instructor, group);
        return group;
    }

    private GroupClass getGroupClass(IdentityManager idm, String name) {
        IdentityQueryBuilder builder = idm.getQueryBuilder();
        Condition cond = builder.equal(GroupClass.NAME, name);
        IdentityQuery<GroupClass> query = builder.createIdentityQuery(GroupClass.class)
                .where(cond);
        return query.getResultList().get(0);
    }
}
