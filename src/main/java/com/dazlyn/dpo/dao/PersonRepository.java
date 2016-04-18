package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.model.Person;
import com.dazlyn.dpo.model.Studio;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Named
@ApplicationScoped
@Slf4j
public class PersonRepository extends AbstractStudioRepository<String, Person> {

    @PersistenceContext
    private EntityManager em;

    public PersonRepository() {
        super(String.class, Person.class);
    }

    public List<Person> findStudentsByStudio(Studio studio) {
        return em.createNamedQuery("Person.findStudentsByStudio", Person.class)
                .setParameter("studio", studio)
                .getResultList();
    }
}
