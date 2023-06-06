package com.shop.service;

import com.shop.UI.Controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public abstract class AbstractService<E> {
    private Class<E>  entityClass;

    public AbstractService(Class<E> entityClass){
        this.entityClass=entityClass;

    }

    public EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory(Controller.PU_NAME);
        return entityManagerFactory.createEntityManager();
    }

    public void create(E entity){
        EntityManager entityManager=getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void edit(E entity){
        EntityManager entityManager=getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }


    public void remove(E entity){
        EntityManager entityManager = getEntityManager();
        if(!entityManager.contains(entity)){
            entity = entityManager.merge(entity);
        }
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void remove(Long id){
        EntityManager entityManager=getEntityManager();
        E entity = entityManager.find(entityClass, id);
        if(entity!=null){
            remove(entity);
        }
    }

    public E find(Long id){
        EntityManager entityManager=getEntityManager();
        return entityManager.find(entityClass,id);
    }

    public List<E> findAll(){
        EntityManager entityManager=getEntityManager();
        CriteriaQuery criteriaQuery=entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
