package com.dazlyn.dpo.data;

import com.dazlyn.dpo.web.security.Studio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import org.picketlink.idm.PartitionManager;

@RequestScoped
public class StudioListProducer {

    @Produces
    @Named
    @Getter
    private List<Studio> studios;

    @Inject
    private PartitionManager partitionManager;

    @PostConstruct
    public void initStudios() {
        studios = partitionManager.getPartitions(Studio.class);
    }
}
