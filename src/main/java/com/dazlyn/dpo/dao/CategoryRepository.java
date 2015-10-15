package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.model.AbstractStudioEntityManager;
import com.dazlyn.dpo.model.CategoryOptionEntity;
import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.model.Studio;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CategoryRepository extends AbstractStudioEntityManager<String, CategoryOptionEntity> {

    public CategoryRepository() {
        super(String.class, CategoryOptionEntity.class);
    }


    public List<CategoryOptionEntity> findForCategory(Studio studio, CategoryType category) {
        return getEntityManager().createNamedQuery("CategoryOption.findForCategory", CategoryOptionEntity.class)
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
    public CategoryOptionEntity findForOption(Studio studio, CategoryType category, String value) {
        List<CategoryOptionEntity> results = getEntityManager().createNamedQuery("CategoryOption.findForOption", CategoryOptionEntity.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .setParameter("value", value)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
