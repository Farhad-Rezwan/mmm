/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.service;

import java.math.BigDecimal;
import java.sql.Date;
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
import rest.Credential;

/**
 *
 * @author farhadullahrezwan
 */
@Stateless
@Path("rest/credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credential entity) {
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
    public Credential find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("findByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Credential> findByUserName(@PathParam("username") String username){
        Query query = em.createNamedQuery("Credential.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPasswordhash/{passwordhash}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findByPasswordHash(@PathParam("passwordhash") String passwordhash){
        Query query = em.createNamedQuery("Credential.findByPasswordhash");
        query.setParameter("passwordhash", passwordhash);
        return query.getResultList();
    }
    
    
    @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findBySignUpDate(@PathParam("signupdate") Date signUpDate){
        Query query = em.createNamedQuery("Credential.findBySignupdate");
        query.setParameter("signupdate", signUpDate);
        return query.getResultList();
    }

    @GET
    @Path("findByPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Credential> findByPersonID(@PathParam("personid") Integer personid) {
	Query query = em.createNamedQuery("Credential.findByPersonID");
	query.setParameter("personid", personid);
    	return query.getResultList();
    }


    @GET
    @Path("credentials")
    @Produces({MediaType.APPLICATION_JSON})
    public Object maxID() {
    List<Object[]> queryList = em.createQuery("SELECT c.username, c.passwordhash FROM Credential c", Object[].class)
            .getResultList();
	    
	
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
        for (Object[] row: queryList){
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("ID", (String)row[0])
		    .add("Name", (String)row[1])
		    .build();
	    
            arrayBuilder.add(memoirObject);
    
    
    
    
	}
	JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    
    }
    
    @GET
    @Path("crede/{username}/{passwordhash}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object credentialCheck(@PathParam("username") String username, @PathParam("passwordhash") String passwordhash) {
    List<Object[]> queryList = em.createQuery("SELECT c.personid.personid, c.personid.firstname FROM Credential c WHERE c.username = :username AND c.passwordhash = :passwordhash", Object[].class)
	    .setParameter("username", username)
	    .setParameter("passwordhash", passwordhash)
            .getResultList();
	    
	
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
        for (Object[] row: queryList){
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("ID", (Integer)row[0])
		    .add("userpersonname", (String)row[1])
		    .build();
	    
            arrayBuilder.add(memoirObject);
    
    
    
    
	}
	JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    
    }
    
    


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
