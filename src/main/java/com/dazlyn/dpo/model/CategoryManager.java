package com.dazlyn.dpo.model;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class CategoryManager {

    @PersistenceContext
    private EntityManager em;

    public List<CategoryOption> findForCategory(Studio studio, Category category) {
        return em.createNamedQuery("CategoryOption.findForCategory", CategoryOption.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .getResultList();
    }

    public CategoryOption find(String uid) {
        return em.find(CategoryOption.class, uid);
    }

    public void remove(CategoryOption option) {
        em.remove(option);
    }

    public void persist(CategoryOption option) {
        em.persist(option);
    }

    public void merge(CategoryOption option) {
        em.merge(option);
    }
}
