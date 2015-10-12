package com.dazlyn.dpo.model;

import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class AbstractEntityManager<K, E> {

    @Inject
    private EntityManager entityManager;

    private final Class<K> keyClass;

    private final Class<E> entityClass;

    public AbstractEntityManager() {
        keyClass = (Class<K>) String.class;
        entityClass = (Class<E>) Object.class;
    }

    public E find(K id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public E find(K id, LockModeType lockMode) {
        return getEntityManager().find(getEntityClass(), id, lockMode);
    }

    public E merge(E entity) {
        return getEntityManager().merge(entity);
    }

    public void persist(E entity) {
        if (entity instanceof AbstractEntity && ((AbstractEntity) entity).getUid() == null) {
            ((AbstractEntity) entity).setUid(UUID.randomUUID().toString());
        }
        getEntityManager().persist(entity);
    }

    public void remove(E entity) {
        getEntityManager().remove(entity);
    }

    public void refresh(E entity) {
        getEntityManager().refresh(entity);
    }
}
