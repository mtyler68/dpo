package com.dazlyn.dpo.model;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class FamilyManager {

    @PersistenceContext
    private EntityManager em;

    public void add(Family family) {
        if (family.getUid() == null) {
            family.setUid(UUID.randomUUID().toString());
        }
        em.persist(family);
    }

    public void update(Family family) {
        em.persist(family);
    }

    public Family find(String uid) {
        return em.find(Family.class, uid);
    }

    public void flush() {
        em.flush();
    }
}
