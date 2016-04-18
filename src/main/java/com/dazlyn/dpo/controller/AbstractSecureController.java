package com.dazlyn.dpo.controller;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.dao.StudioRepository;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.picketlink.Identity;

public abstract class AbstractSecureController {

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private Identity identity;

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private StudioRepository studioManager;

    protected Studio geStudio() {
        String id = identity.getAccount().getPartition().getId();
        return studioManager.findByRealmId(id);
    }
}
