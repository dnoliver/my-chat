/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Profiles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author dnoliver
 */
@Stateless
@Path("entity.profiles")
public class ProfilesFacadeREST extends AbstractFacade<Profiles> {
    @PersistenceContext(unitName = "com.das.chatPU")
    private EntityManager em;

    public ProfilesFacadeREST() {
        super(Profiles.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public Response create(Profiles entity) {
        List<Profiles> resultList = em.createNamedQuery("Profiles.findByLogin",Profiles.class)
                .setParameter("login", entity.getLogin())
                .getResultList();
        
        if(resultList.isEmpty()){
            return super.create(entity);
        }
        else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, Profiles entity) {
        return super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        return super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Profiles find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("login/{login}")
    @Produces({"application/xml", "application/json"})
    public Profiles findByLogin(@PathParam("login") String login) {
        try {
            return em.createNamedQuery("Profiles.findByLogin",Profiles.class)
                .setParameter("login",login)
                .getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
    
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Profiles> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Profiles> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
