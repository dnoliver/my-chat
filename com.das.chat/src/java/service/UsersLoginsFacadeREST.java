/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.UsersLogins;
import java.util.Date;
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
@Path("entity.userslogins")
public class UsersLoginsFacadeREST extends AbstractFacade<UsersLogins> {
    @PersistenceContext(unitName = "com.das.chatPU")
    private EntityManager em;

    public UsersLoginsFacadeREST() {
        super(UsersLogins.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public Response create(UsersLogins entity) {
        return super.create(entity);
    }
    
    @POST
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response executeAction(
            @PathParam("id") Integer id,
            @QueryParam("action") String action
    ) {
        UsersLogins entity = super.find(id);
        
        if(action.equals("terminate")){
            entity.setDatetimeOfAccessEnd(new Date());
            return super.edit(entity);
        }
        else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public Response edit(@PathParam("id") Integer id, UsersLogins entity) {
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
    public UsersLogins find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<UsersLogins> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<UsersLogins> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
