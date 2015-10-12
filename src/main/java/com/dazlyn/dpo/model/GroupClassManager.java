package com.dazlyn.dpo.model;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class GroupClassManager extends AbstractEntityManager<String, GroupClass> {

    public GroupClassManager() {
        super(String.class, GroupClass.class);
    }

    public List<GroupClass> findAllByStudio(Studio studio) {
        return findAllByStudio(studio, false);
    }

    public List<GroupClass> findAllByStudio(Studio studio, boolean archived) {
        return getEntityManager().createNamedQuery("GroupClass.findAllByStudio", GroupClass.class)
                .setParameter("studio", studio)
                .setParameter("archived", archived)
                .getResultList();
    }
}
