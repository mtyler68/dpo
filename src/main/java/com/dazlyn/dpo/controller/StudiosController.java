package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.dao.CategoryRepository;
import com.dazlyn.dpo.model.StudioEntity;
import com.dazlyn.dpo.dao.StudioRepository;
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
    private CategoryRepository categoryRepo;
    
    @Inject
    private RealmManager realmManager;

    @Inject
    private StudioRepository studioManager;
    

    @Inject
    private FacesContext facesContext;

    @Transactional
    public void save() {

        // TODO: Needs validation
        Realm realm = realmManager.createRealm(code);
        StudioEntity studio = StudioEntity.builder()
                .code(code)
                .name(studioName)
                .realmId(realm.getId())
                .build();
        studioManager.persist(studio);
        categoryRepo.addDefaultCategories(studio);

        code = "";
        studioName = "";

        facesContext.addMessage(null, new FacesMessage("Studio Added",
                String.format("Added new studio \"%s\"", studioName)));
    }
}
