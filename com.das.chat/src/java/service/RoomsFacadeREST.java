/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Messages;
import entity.Profiles;
import entity.Rooms;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author dnoliver
 */
@Stateless
@Path("entity.rooms")
public class RoomsFacadeREST extends AbstractFacade<Rooms> {
    @PersistenceContext(unitName = "com.das.chatPU")
    private EntityManager em;

    public RoomsFacadeREST() {
        super(Rooms.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public Response create(Rooms entity) {
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, Rooms entity) {
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
    public Rooms find(@PathParam("id") Integer id) {
        return super.find(id);
    }
 
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Rooms> findAll() {
        return super.findAll();
    }

    @GET
    @Path("type/public")
    @Produces({"application/xml", "application/json"})
    public List<Rooms> findPublics() {
        return em.createNamedQuery("Rooms.findByType", Rooms.class)
                .setParameter("type", "public")
                .getResultList();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Rooms> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
