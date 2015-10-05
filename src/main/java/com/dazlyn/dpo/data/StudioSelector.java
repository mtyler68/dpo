package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.annotations.PicketLink;

@SessionScoped
@Named
public class StudioSelector implements Serializable {

    @Inject
    private StudioManager studioManager;

    private Studio studio;

    @Produces
    @Named("studio")
    @PicketLink
    public Studio select() {
        return studio;
    }

    public void setStudio(String studioId) {
        this.studio = studioManager.findStudio(studioId);
    }

    public String getStudio() {
        return studio == null ? "" : studio.getName();
    }
}
