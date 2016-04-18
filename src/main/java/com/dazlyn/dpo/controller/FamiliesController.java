package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.FamilyManager;
import com.dazlyn.dpo.dao.StudioRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.Identity;

@Named
@ViewScoped
@Slf4j
public class FamiliesController implements Serializable {

    @Inject
    private Identity identity;

    @Inject
    private volatile StudioRepository studioManager;

    @Inject
    private FamilyManager familyManager;

    @Getter
    private List<Family> families;

    @Getter
    @Setter
    private List<Family> filteredFamilies;

    @Getter
    @Setter
    private Family selectedFamily;

    @PostConstruct
    public void init() {
        String id = identity.getAccount().getPartition().getId();
//        Studio studio = studioManager.findByRealmIdWithFamilies(id);
//        families = studio.getFamilies();
        families = new ArrayList<>();
    }

    @Transactional
    public void removeSelectedFamily() {
        log.info("action=removeSelectedFamily, studio={}, family={}", null, selectedFamily);
        if (selectedFamily == null) {
            return;
        }

        Family emFamilty = familyManager.find(selectedFamily.getUid());
        if (emFamilty != null) {
            familyManager.remove(emFamilty);
            families.remove(selectedFamily);
            if (filteredFamilies != null) {
                filteredFamilies.remove(selectedFamily);
            }
            // TODO: Need to move to archived state actually
            // TODO: Need to delete all family members too and other related objects!!!
            selectedFamily = null;
        } else {
            log.warn("action=removeSelectedFamily, studo={}, family={}, message=\"could not find selected family in db\"",
                    null, selectedFamily);
        }
    }
}
