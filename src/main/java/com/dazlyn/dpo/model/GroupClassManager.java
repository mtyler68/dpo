package com.dazlyn.dpo.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.query.Condition;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

@Named
@ApplicationScoped
public class GroupClassManager {

    @Inject
    private PartitionManager partitionManager;

    public GroupClass createGroupClass(Studio studio, String title) {
        IdentityManager idm = partitionManager.createIdentityManager(studio);

        String path = String.format("/%s/classes/%s", studio.getName(), title);
        GroupClass group = new GroupClass(title);
        group.setPath(path);
        idm.add(group);

        return group;
    }

    public GroupClass findGroupClass(Studio studio, String name) {
        IdentityManager idm = partitionManager.createIdentityManager(studio);

        IdentityQueryBuilder builder = idm.getQueryBuilder();
        Condition cond = builder.equal(GroupClass.NAME, name);
        IdentityQuery<GroupClass> query = builder.createIdentityQuery(GroupClass.class)
                .where(cond);
        return query.getResultList().get(0);
    }
}
