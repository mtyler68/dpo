package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.dao.StudioRepository;
import com.dazlyn.dpo.model.StudioSettings;
import com.dazlyn.dpo.model.StudioSettingsManager;
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
    private StudioRepository studioManager;

    @Inject
    private StudioSettingsManager studioSettingsManager;

    private StudioSettings studioSettings;

    @Named("studio")
    @Produces
    public Studio retrieve() {
        log.info("action=retrieve");
        String id = identity.getAccount().getPartition().getId();
        return studioManager.findByRealmId(id);
    }

    @Named
    @Produces
    public StudioSettings getStudioSettings() {
        if (studioSettings == null) {
            studioSettings = studioSettingsManager.findForStudio(retrieve());
        }
        return studioSettings;
    }
}
