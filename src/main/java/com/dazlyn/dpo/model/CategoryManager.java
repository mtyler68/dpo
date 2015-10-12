package com.dazlyn.dpo.model;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class CategoryManager extends AbstractEntityManager<String, CategoryOption> {

    @PersistenceContext
    private EntityManager em;

    public List<CategoryOption> findForCategory(Studio studio, Category category) {
        return em.createNamedQuery("CategoryOption.findForCategory", CategoryOption.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .getResultList();
    }

    /**
     *
     * @param studio
     * @param category
     * @param value
     * @return Can return null if not found.
     */
    public CategoryOption findForOption(Studio studio, Category category, String value) {
        List<CategoryOption> results = em.createNamedQuery("CategoryOption.findForOption", CategoryOption.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .setParameter("value", value)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
