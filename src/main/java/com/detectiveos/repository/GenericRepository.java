package com.detectiveos.repository;

import com.detectiveos.config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

public abstract class GenericRepository<T> {
    private final Class<T> type;
    protected GenericRepository(Class<T> type){this.type=type;}
    public T save(T entity){return tx(em->{em.persist(entity);return entity;});}
    public T update(T entity){return tx(em->{return em.merge(entity);});}
    public T find(Long id){try(EntityManager em=JPAUtil.createEntityManager()){return em.find(type,id);}}
    public List<T> findAll(){try(EntityManager em=JPAUtil.createEntityManager()){return em.createQuery("from "+type.getSimpleName(),type).getResultList();}}
    protected <R> R tx(Function<EntityManager,R> work){try(EntityManager em=JPAUtil.createEntityManager()){var tx=em.getTransaction();tx.begin();try{R result=work.apply(em);tx.commit();return result;}catch(RuntimeException e){if(tx.isActive())tx.rollback();throw e;}}}
}
