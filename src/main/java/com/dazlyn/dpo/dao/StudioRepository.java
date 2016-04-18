package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.model.Studio;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Named
@Slf4j
public class StudioRepository extends AbstractRepository<String, Studio> {

    public StudioRepository() {
        super(String.class, Studio.class);
    }

    public Studio findByRealmId(String realmId) {
        TypedQuery<Studio> query = getEntityManager()
                .createNamedQuery("Studio.findByRealmId", Studio.class)
                .setParameter("realmId", realmId);
        List results = query.getResultList();
        return (Studio) (results.isEmpty() ? null : results.get(0));
    }
}
