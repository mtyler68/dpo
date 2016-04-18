package com.dazlyn.dpo.security;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.IdentityQueryBuilder;

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

    public User find(Realm realm, String username) {
        IdentityManager idm = partitionManager.createIdentityManager(realm);
        IdentityQueryBuilder queryBuilder = idm.getQueryBuilder();
        List<User> results = queryBuilder.createIdentityQuery(User.class)
                .where(queryBuilder.equal(User.LOGIN_NAME, username))
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
