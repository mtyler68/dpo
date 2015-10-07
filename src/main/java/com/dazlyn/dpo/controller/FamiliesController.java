package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.picketlink.Identity;

@Named
@ViewScoped
public class FamiliesController implements Serializable {

    @Inject
    private Identity identity;

    @Inject
    private volatile StudioManager studioManager;

    @Getter
    private List<Family> families;

    @Getter
    @Setter
    private List<Family> filteredFamilies;

    @PostConstruct
    public void init() {
        String id = identity.getAccount().getPartition().getId();
        Studio studio = studioManager.findByRealmIdWithFamilies(id);
        families = studio.getFamilies();
    }
}
