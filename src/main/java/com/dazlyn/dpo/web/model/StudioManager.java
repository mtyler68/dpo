package com.dazlyn.dpo.web.model;

import com.dazlyn.dpo.web.security.Studio;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;

@ApplicationScoped
@Named
public class StudioManager {

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private RelationshipManager relationshipManager;

    public Studio createStudio(String code, String fullName) {
        Studio studio = new Studio(code);
        studio.setBusinessName(fullName);

        partitionManager.add(studio);
        IdentityManager idm = partitionManager.createIdentityManager(studio);

        idm.add(new Role(StudioRole.ADMIN.name()));
        idm.add(new Role(StudioRole.STUDIO_ASSISTANT.name()));
        idm.add(new Role(StudioRole.STUDIO_BOOKKEEPER.name()));
        idm.add(new Role(StudioRole.STUDIO_CLIENT.name()));
        idm.add(new Role(StudioRole.STUDIO_DIRECTOR.name()));
        idm.add(new Role(StudioRole.STUDIO_INSTRUCTOR.name()));
        idm.add(new Role(StudioRole.STUDIO_EMPLOYEE.name()));

        return studio;
    }

    public Studio findStudio(String code) {
        return partitionManager.getPartition(Studio.class, code);
    }

    public void grantRoles(Studio studio, Person person, StudioRole... roles) {
        IdentityManager idm = partitionManager.createIdentityManager(studio);
        for (StudioRole role : roles) {
            BasicModel.grantRole(relationshipManager, person, BasicModel.getRole(idm, role.name()));
        }
    }
}
