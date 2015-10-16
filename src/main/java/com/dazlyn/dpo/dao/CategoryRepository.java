package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.model.CategoryEntity;
import com.dazlyn.dpo.model.CategoryType;
import com.dazlyn.dpo.model.StudioEntity;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CategoryRepository extends AbstractStudioRepository<String, CategoryEntity> {

    private static final Object[][] DEFAULT_CATEGORIES = {
        {CategoryType.BILLING_SCHEDULE, "Monthly", "Quarterly", "Yearly", "Drop In Class", "Weekly"},
        {CategoryType.CHARGE_CATEGORY, "2015-10 October", "2015-11 November", "2015-12 December",
            "2016 Spring Recital Fee", "Workshop Fee", "Registration Fee"},
        {CategoryType.CLASS_GENRE, "Ballet", "Contemporary", "Hip Hop", "Jazz", "Pointe", "Tap", "Combo"},
        {CategoryType.CLASS_LEVEL, "Toddler", "Pre-school", "Kindergarten", "Beginner", "Intermediate", "Advanced"},
        {CategoryType.CLASS_LOCATION, "Coronado", "Downtown"},
        {CategoryType.CLASS_PROGRAM, "Recital", "Competition"},
        {CategoryType.PAYMENT_CYCLE, "Billing Schedule", "Drop-in"},
        {CategoryType.PAYMENT_METHOD, "Credit Card", "Cash", "Check", "Gift Certificate", "Other", "PayPal", "Scholarship"},
        {CategoryType.PAYMENT_SCHEDULE, "Monthly Payments", "Quarterly Payments", "Yearly Payments"}
    };
    
    public CategoryRepository() {
        super(String.class, CategoryEntity.class);
    }

    public List<CategoryEntity> findAllForType(StudioEntity studio, CategoryType category) {
        return findAllForType(studio, category, false);
    }

    public List<CategoryEntity> findAllForType(StudioEntity studio, CategoryType category, boolean archived) {
        return getEntityManager().createNamedQuery("CategoryEntity.findAllForType", CategoryEntity.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .setParameter("archived", archived)
                .getResultList();
    }

    /**
     *
     * @param studio
     * @param category
     * @param value
     * @return Can return null if not found.
     */
    public CategoryEntity findForOption(StudioEntity studio, CategoryType category, String value) {
        List<CategoryEntity> results = getEntityManager().createNamedQuery("CategoryOption.findForOption", CategoryEntity.class)
                .setParameter("studio", studio)
                .setParameter("category", category)
                .setParameter("value", value)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    public void addDefaultCategories(StudioEntity studio) {
        for (Object[] options : DEFAULT_CATEGORIES) {
            CategoryType type = (CategoryType) options[0];
            for (int ndx = 1; ndx < options.length; ndx++) {
                CategoryEntity category = CategoryEntity.builder()
                        .type(type)
                        .sortOrder(ndx)
                        .value((String) options[ndx])
                        .build();
                category.setStudio(studio);
                persist(category);
            }
            flush();
        }
    }
}
