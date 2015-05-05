/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Messages;
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
@Path("entity.messages")
public class MessagesFacadeREST extends AbstractFacade<Messages> {
    @PersistenceContext(unitName = "com.das.chatPU")
    private EntityManager em;

    public MessagesFacadeREST() {
        super(Messages.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public Response create(Messages entity) {
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, Messages entity) {
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
    public Messages find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("room/{room}")
    @Produces({"application/xml", "application/json"})
    public List<Messages> findByRoom(
            @PathParam("room") Integer roomId
    ) {
        Rooms room = em.find(Rooms.class, roomId);
        
        return em.createNamedQuery("Messages.findByRoom",Messages.class)
                .setParameter("room", room)
                .getResultList();
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Messages> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Messages> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
