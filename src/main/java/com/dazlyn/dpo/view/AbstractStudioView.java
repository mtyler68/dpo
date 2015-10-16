package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.StudioEntity;
import com.dazlyn.dpo.dao.StudioRepository;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.picketlink.Identity;

public abstract class AbstractStudioView implements Serializable {

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private transient Identity identity;

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private transient StudioRepository studioManager;

    @Getter(AccessLevel.PROTECTED)
    private transient StudioEntity studio;

    @PostConstruct
    public void initStudio() {
        String id = identity.getAccount().getPartition().getId();
        studio = studioManager.findByRealmId(id);
    }
}
