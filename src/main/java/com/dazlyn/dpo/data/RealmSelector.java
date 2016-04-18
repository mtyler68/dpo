package com.dazlyn.dpo.data;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.annotations.PicketLink;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Realm;

@SessionScoped
@Named
public class RealmSelector implements Serializable {

    @Inject
    private PartitionManager partitionManager;

    private Realm realm;

    @Produces
    @Named("realm")
    @PicketLink
    public Realm select() {
        return realm;
    }

    public void setRealm(String realmId) {
        this.realm = partitionManager.getPartition(Realm.class, realmId);
    }

    public String getRealm() {
        return realm == null ? "" : realm.getName();
    }
}
