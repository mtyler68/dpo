package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.Identity;

@RequestScoped
@Slf4j
public class StudioProducer {

    @Inject
    private Identity identity;

    @Inject
    private StudioManager studioManager;

    @Named("studio")
    @Produces
    public Studio retrieve() {
        log.info("action=retrieve");
        String id = identity.getAccount().getPartition().getId();
        return studioManager.findByRealmId(id);
    }
}
