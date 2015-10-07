package com.dazlyn.dpo.model;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Named
@ApplicationScoped
@Slf4j
public class PersonManager {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void add(Person person) {
        if (person.getUid() == null) {
            person.setUid(UUID.randomUUID().toString());
        }
        em.persist(person);
    }
}
