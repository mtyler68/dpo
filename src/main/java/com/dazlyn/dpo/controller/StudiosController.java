package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.StudioRole;
import com.dazlyn.dpo.model.Studio;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Role;
import org.primefaces.context.RequestContext;

@Model
public class StudiosController {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String studioName;

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private FacesContext facesContext;

    public void addStudio() {
        RequestContext.getCurrentInstance().openDialog("/WEB-INF/dialogs/studioedit.xhtml");
    }

    @Transactional
    public void save() {
        createStudio(code, studioName);

        facesContext.addMessage(null, new FacesMessage("Studio Added",
                String.format("Added new studio \"%s\"", studioName)));
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

}
