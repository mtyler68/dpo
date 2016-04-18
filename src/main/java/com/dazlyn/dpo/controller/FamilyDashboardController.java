package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Family;
import com.dazlyn.dpo.model.FamilyManager;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.util.Faces;

@Named
@ViewScoped
@Slf4j
public class FamilyDashboardController implements Serializable {

    @Getter
    @Setter
    private Family family;

    @Inject
    private FamilyManager familyManager;

    @PostConstruct
    public void init() {
        String uid = Faces.getRequestParameter("familyUid");
        log.info("action=init, familyUid={}", uid);
        this.family = familyManager.find(uid);
    }
}
