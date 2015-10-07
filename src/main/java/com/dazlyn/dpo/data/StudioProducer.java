package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.Identity;

@RequestScoped
public class StudioProducer {

    @Inject
    private Identity identity;

    @Inject
    private StudioManager studioManager;

    @Named("studio")
    @Produces
    public Studio retrieve() {
        if (identity != null && identity.getAccount() != null && identity.getAccount().getPartition() != null) {
            String id = identity.getAccount().getPartition().getId();
            return studioManager.findByRealmId(id);
        }
        return null;
    }
}
