/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import rest.Cinema;

/**
 *
 * @author farhadullahrezwan
 */
@Stateless
@Path("rest/cinema")
public class CinemaFacadeREST extends AbstractFacade<Cinema> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public CinemaFacadeREST() {
        super(Cinema.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Cinema entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cinema entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Cinema find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("findByCinemaname/{cinemaname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findByCinemaName(@PathParam("cinemaname") String cinemaname) {
        Query query = em.createNamedQuery("Cinema.findByCinemaname");
        query.setParameter("cinemaname", cinemaname);
        return query.getResultList();
    }
    @GET
    @Path("findByCinemasuburb/{cinemasuburb}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Cinema> findByCinemaSuburb(@PathParam("cinemasuburb") String cinemasuburb) {
        Query query = em.createNamedQuery("Cinema.findByCinemasuburb");
	query.setParameter("cinemasuburb", cinemasuburb);
	return query.getResultList();
    }

   @GET
    @Path("getAllCinemaNameAndSuburb")
    @Produces({MediaType.APPLICATION_JSON})
    public Object getCinemaNameSuburb(){
    List<Object[]> queryList = em.createQuery("SELECT c.cinemaname, c.cinemasuburb FROM Cinema c", Object[].class)
            .getResultList();
	    
	
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
        for (Object[] row: queryList){
            JsonObject cinemaObject = Json.createObjectBuilder()
		    .add("cinemaname", (String)row[0])
		    .add("cinemasuburb", (String)row[1])
		    .build();
	    
            arrayBuilder.add(cinemaObject);
    
    
    
    
	}
	JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

