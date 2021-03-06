package com.dazlyn.dpo.security;

import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Group;

@Named
public class AuthorizationChecker {

    @Inject
    private Identity identity;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    public boolean hasRealmRole(String roleName) {
//        Role role = BasicModel.getRole(this.identityManager, roleName);
//        return BasicModel.hasRole(this.relationshipManager, this.identity.getAccount(), role);
        return true;
    }

    public boolean hasAnyRealmRole(String... roleNames) {
//        for (String roleName : roleNames) {
//            if (hasRealmRole(roleName)) {
//                return true;
//            }
//        }
//        return false;
        return true;
    }

    public boolean hasAnyRealmRole(ArrayList roleNames) {
//        for (Object roleName : roleNames) {
//            if (hasRealmRole((String) roleName)) {
//                return true;
//            }
//        }
//        return false;
        return true;
    }

    public boolean isMember(String groupName) {
        Group group = BasicModel.getGroup(this.identityManager, groupName);
        return BasicModel.isMember(this.relationshipManager, this.identity.getAccount(), group);
    }

    public boolean hasGroupRole(String roleName, String groupName) {
        Group group = BasicModel.getGroup(this.identityManager, groupName);
        Role role = BasicModel.getRole(this.identityManager, roleName);
        return BasicModel.hasGroupRole(this.relationshipManager, this.identity.getAccount(), role, group);
    }
}
