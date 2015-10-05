package com.dazlyn.dpo.data;

import com.dazlyn.dpo.model.GroupClass;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import org.picketlink.Identity;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.GroupMembership;
import org.picketlink.idm.query.RelationshipQuery;

@RequestScoped
public class MyGroupClassesProducer {

    @Inject
    private Identity identity;

    @Inject
    private RelationshipManager relationshipManager;

    @Produces
    @Named
    public List<GroupClass> myGroupClasses() {
        RelationshipQuery<GroupMembership> query = relationshipManager.createRelationshipQuery(GroupMembership.class);
        return null;
    }
}
