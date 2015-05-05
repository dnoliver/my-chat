/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Invitations;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author dnoliver
 */
@Stateless
@Path("entity.invitations")
public class InvitationsFacadeREST extends AbstractFacade<Invitations> {
    @PersistenceContext(unitName = "com.das.chatPU")
    private EntityManager em;

    public InvitationsFacadeREST() {
        super(Invitations.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public Response create(Invitations entity) {
        return super.create(entity);
    }
    
    @POST
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response executeAction(
            @PathParam("id") Integer id,
            @QueryParam("action") String action
    ){
        Invitations entity = super.find(id);
        
        if(entity == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
                
        if(action.equals("accept")){
            entity.setState("accepted");
            super.edit(entity);
            return Response.status(Response.Status.OK).build();
        }
        else if(action.equals("reject")){
            entity.setState("rejected");
            super.edit(entity);
            return Response.status(Response.Status.OK).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, Invitations entity) {
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
    public Invitations find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("sender/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Invitations> findBySender(
        @PathParam("id") Integer senderId
    ) {
        Profiles sender = em.find(Profiles.class, senderId);

        return em.createNamedQuery("Invitations.findBySender", Invitations.class)
                .setParameter("sender", sender)
                .getResultList();
    }
    
    @GET
    @Path("receiver/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Invitations> findByReceiver(
        @PathParam("id") Integer receiverId
    ) {
        Profiles receiver = em.find(Profiles.class, receiverId);

        return em.createNamedQuery("Invitations.findByReceiver", Invitations.class)
                .setParameter("receiver", receiver)
                .getResultList();
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Invitations> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Invitations> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
