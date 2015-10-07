package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.Studio;
import com.dazlyn.dpo.model.StudioManager;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;

@RequestScoped
public class StudioListProducer {

    @Produces
    @Named
    @Getter
    private List<Studio> studios;

    @Inject
    private StudioManager studioManager;

    @PostConstruct
    public void initStudios() {
        studios = studioManager.findAll();
    }
}
