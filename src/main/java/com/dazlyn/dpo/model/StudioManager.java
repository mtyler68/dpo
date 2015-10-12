package com.dazlyn.dpo.model;

import com.dazlyn.dpo.security.RealmManager;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;

@ApplicationScoped
@Named
@Slf4j
public class StudioManager {

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private RelationshipManager relationshipManager;

    @Inject
    private RealmManager realmManager;

    @Inject
    private EntityManager em;

    private static final Object[][] CATEGORIES = {
        {Category.BILLING_SCHEDULE, "Monthly", "Quarterly", "Yearly", "Drop In Class", "Weekly"},
        {Category.CHARGE_CATEGORY, "2015-10 October", "2015-11 November", "2015-12 December",
            "2016 Spring Recital Fee", "Workshop Fee", "Registration Fee"},
        {Category.CLASS_GENRE, "Ballet", "Contemporary", "Hip Hop", "Jazz", "Pointe", "Tap", "Combo"},
        {Category.CLASS_LEVEL, "Toddler", "Pre-school", "Kindergarten", "Beginner", "Intermediate", "Advanced"},
        {Category.CLASS_LOCATION, "Coronado", "Downtown"},
        {Category.CLASS_PROGRAM, "Recital", "Competition"},
        {Category.PAYMENT_METHOD, "Credit Card", "Cash", "Check", "Gift Certificate", "Other", "PayPal", "Scholarship"},
        {Category.PAYMENT_SCHEDULE, "Monthly Payments", "Quarterly Payments", "Yearly Payments"}
    };

    @Transactional
    public void add(Studio studio) {

        if (studio.getUid() == null) {
            studio.setUid(UUID.randomUUID().toString());
        }
        em.persist(studio);
        addCategories(studio);
    }

    public List<Studio> findAll() {
        return em.createNamedQuery("Studio.findAll", Studio.class)
                .getResultList();
    }

    public Studio findByRealmId(String realmId) {
        TypedQuery<Studio> query = em.createNamedQuery("Studio.findByRealmId", Studio.class)
                .setParameter("realmId", realmId);
        List results = query.getResultList();
        return (Studio) (results.isEmpty() ? null : results.get(0));
    }

    public Studio findByRealmIdWithFamilies(String realmId) {
        TypedQuery<Studio> query = em.createNamedQuery("Studio.findByRealmIdWithFamilies", Studio.class)
                .setParameter("realmId", realmId);
        List results = query.getResultList();
        return (Studio) (results.isEmpty() ? null : results.get(0));
    }

    private void addCategories(Studio studio) {
        for (Object[] options : CATEGORIES) {
            Category category = (Category) options[0];
            for (int optionIndex = 1; optionIndex < options.length; optionIndex++) {
                CategoryOption option = CategoryOption.builder()
                        .category(category)
                        .sortOrder(optionIndex)
                        .studio(studio)
                        .uid(UUID.randomUUID().toString())
                        .value((String) options[optionIndex])
                        .build();
                em.persist(option);
            }
            em.flush();
        }
    }

}
