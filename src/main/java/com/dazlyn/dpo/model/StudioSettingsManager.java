package com.dazlyn.dpo.model;

import com.dazlyn.dpo.dao.AbstractRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class StudioSettingsManager extends AbstractRepository<String, StudioSettings> {

    public StudioSettingsManager() {
        super(String.class, StudioSettings.class);
    }

    public StudioSettings findForStudio(Studio stuio) {
        return getEntityManager().createNamedQuery("StudioSettings.findForStudio", StudioSettings.class)
                .setParameter("studio", stuio)
                .getSingleResult();
    }

}
