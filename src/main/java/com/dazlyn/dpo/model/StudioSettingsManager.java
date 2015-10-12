package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class StudioSettingsManager extends AbstractEntityManager<String, StudioSettings> {

    public StudioSettingsManager() {
        super(String.class, StudioSettings.class);
    }

    public StudioSettings findForStudio(Studio stuio) {
        return getEntityManager().createNamedQuery("StudioSettings.findForStudio", StudioSettings.class)
                .setParameter("studio", stuio)
                .getSingleResult();
    }

}
