package com.dazlyn.dpo.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;

@Named
@ApplicationScoped
public class UserManager {

    @Inject
    private PartitionManager partitionManager;

    public User createUser(Realm realm, String username, String password, String email, String first, String last) {
        User user = new User(username);
        user.setEmail(email);
        user.setFirstName(first);
        user.setLastName(last);

        IdentityManager idm = partitionManager.createIdentityManager(realm);
        idm.add(user);
        idm.updateCredential(user, new Password(password));

        return user;
    }

}
