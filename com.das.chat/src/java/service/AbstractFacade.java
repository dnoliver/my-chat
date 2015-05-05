/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dnoliver
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public Response create(T entity) {
        try {
            getEntityManager().persist(entity);
            return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(EntityExistsException ex){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    public Response edit(T entity) {
        try {
            getEntityManager().merge(entity);
            return Response.ok(entity, MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(IllegalArgumentException ex){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }        
    }

    public Response remove(T entity) {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            return Response.ok(entity, MediaType.APPLICATION_JSON).build();
        }
        catch(IllegalArgumentException ex){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }  
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
