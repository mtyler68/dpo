package com.dazlyn.dpo.view;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
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
    private transient StudioManager studioManager;

    @Getter(AccessLevel.PROTECTED)
    private transient Studio studio;

    @PostConstruct
    public void initStudio() {
        String id = identity.getAccount().getPartition().getId();
        studio = studioManager.findByRealmId(id);
    }
}
