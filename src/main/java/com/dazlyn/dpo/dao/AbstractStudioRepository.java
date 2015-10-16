package com.dazlyn.dpo.dao;

import com.dazlyn.dpo.model.AbstractStudioEntity;
import com.dazlyn.dpo.model.StudioEntity;
import java.util.Date;
import java.util.List;

public abstract class AbstractStudioRepository<K, E> extends AbstractRepository<K, E> {

    protected AbstractStudioRepository(Class<K> keyClass, Class<E> entityClass) {
        super(keyClass, entityClass);
    }

    @Override
    public List<E> findAll() {
        return findAll(false);
    }

    public List<E> findAll(boolean isArchived) {
        return getEntityManager().createQuery(
                "SELECT x FROM " + getEntityClass().getSimpleName() + " x WHERE x.archived = :isArchived", getEntityClass())
                .setParameter("isArchived", isArchived)
                .getResultList();
    }

    public List<E> findAll(StudioEntity studio) {
        return findAll(studio, false);
    }

    public List<E> findAll(StudioEntity studio, boolean isArchived) {
        return getEntityManager().createQuery(
                "SELECT x FROM " + getEntityClass().getSimpleName() + " x WHERE x.studio = :studio AND x.archived = :isArchived", getEntityClass())
                .setParameter("studio", studio)
                .setParameter("isArchived", isArchived)
                .getResultList();
    }

    public void archive(E entity) {
        if (entity instanceof AbstractStudioEntity && !((AbstractStudioEntity) entity).isArchived()) {
            ((AbstractStudioEntity) entity).setArchived(true);
            ((AbstractStudioEntity) entity).setArchivedTimestamp(new Date());
            merge(entity);
        }
    }

}
