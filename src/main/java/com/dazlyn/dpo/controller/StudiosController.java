package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import com.dazlyn.dpo.security.RealmManager;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.picketlink.idm.model.basic.Realm;

@Model
public class StudiosController {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String studioName;

    @Inject
    private RealmManager realmManager;

    @Inject
    private StudioManager studioManager;

    @Inject
    private FacesContext facesContext;

    @Transactional
    public void save() {

        // TODO: Needs validation
        Realm realm = realmManager.createRealm(code);
        Studio studio = Studio.builder()
                .code(code)
                .name(studioName)
                .realmId(realm.getId())
                .build();
        studioManager.add(studio);

        code = "";
        studioName = "";

        facesContext.addMessage(null, new FacesMessage("Studio Added",
                String.format("Added new studio \"%s\"", studioName)));
    }
}
