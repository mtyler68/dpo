package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.PartitionManager;

@Named
@ApplicationScoped
public class GroupClassManager {

    @Inject
    private PartitionManager partitionManager;

}
