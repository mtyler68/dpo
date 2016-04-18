package com.dazlyn.dpo.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;

@Named
@ApplicationScoped
@Slf4j
public class RealmManager {

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private RelationshipManager relationshipManager;


    public Realm createRealm(String name) {
        Realm realm = new Realm(name);
        partitionManager.add(realm);

        IdentityManager idm = partitionManager.createIdentityManager(realm);

        for (RealmRole value : RealmRole.values()) {
            idm.add(new Role(value.name()));
        }

        return realm;
    }

    public void grantRoles(Realm realm, User user, RealmRole... roles) {
        IdentityManager idm = partitionManager.createIdentityManager(realm);
        for (RealmRole role : roles) {
            log.info("action=grantRoles, realm={}, person={}, role={}",
                    realm.getName(),
                    user.getLoginName(),
                    role.name());
            BasicModel.grantRole(relationshipManager, user, BasicModel.getRole(idm, role.name()));
        }
    }

    public Realm findById(String id) {
        return partitionManager.lookupById(Realm.class, id);
    }
}
