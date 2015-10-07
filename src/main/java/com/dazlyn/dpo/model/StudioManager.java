package com.dazlyn.dpo.model;

import com.dazlyn.dpo.model.Studio;
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

    @Transactional
    public void add(Studio studio) {

        if (studio.getUid() == null) {
            studio.setUid(UUID.randomUUID().toString());
        }
        em.persist(studio);
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

}
