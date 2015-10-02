package com.dazlyn.dpo.web;

import com.dazlyn.dpo.web.security.Studio;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.annotations.PicketLink;
import org.picketlink.idm.PartitionManager;

@SessionScoped
@Named
public class StudioSelector implements Serializable {

    @Inject
    private PartitionManager partitionManager;

    private Studio studio;

    @Produces
    @Named("studio")
    @PicketLink
    public Studio select() {
        return studio;
    }

    public void setStudio(String studioId) {
        System.out.println("setStudio()=" + studioId);
        this.studio = partitionManager.getPartition(Studio.class, studioId);
        System.out.println("studio=" + this.studio);
    }

    public String getStudio() {
        return studio == null ? "" : studio.getName();
    }
}
