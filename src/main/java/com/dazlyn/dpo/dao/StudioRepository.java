package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.dao.AbstractRepository;
import com.dazlyn.dpo.model.StudioEntity;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Named
@Slf4j
public class StudioRepository extends AbstractRepository<String, StudioEntity> {

    public StudioRepository() {
        super(String.class, StudioEntity.class);
    }
    
    public StudioEntity findByRealmId(String realmId) {
        TypedQuery<StudioEntity> query = getEntityManager()
                .createNamedQuery("Studio.findByRealmId", StudioEntity.class)
                .setParameter("realmId", realmId);
        List results = query.getResultList();
        return (StudioEntity) (results.isEmpty() ? null : results.get(0));
    }

    public StudioEntity findByRealmIdWithFamilies(String realmId) {
        TypedQuery<StudioEntity> query = getEntityManager()
                .createNamedQuery("Studio.findByRealmIdWithFamilies", StudioEntity.class)
                .setParameter("realmId", realmId);
        List results = query.getResultList();
        return (StudioEntity) (results.isEmpty() ? null : results.get(0));
    }
}
