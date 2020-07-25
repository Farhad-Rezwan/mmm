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
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import rest.Person;

/**
 *
 * @author farhadullahrezwan
 */
@Stateless
@Path("rest/person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
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
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByFirstname/{firstname}")
    @Produces({"application/json"})
    public List<Person> findByFirstname(@PathParam("firstname") String firstname) {
        Query query = em.createNamedQuery("Person.findByFirstname"); 
        query.setParameter("firstname", firstname);
        return query.getResultList();
    }
//    
    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Person> findBySurname(@PathParam("surname") String surname) {
        Query query = em.createNamedQuery("Person.findBySurname"); 
        query.setParameter("surname", surname);
        return query.getResultList();
    }
    
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Person> findByGender(@PathParam("gender") String gender) {
        Query query = em.createNamedQuery("Person.findByGender");
        char c = gender.charAt(0);
        query.setParameter("gender", c);
        return query.getResultList();
    }
 
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Person> findByDob(@PathParam("dob") Date dob) {
        Query query = em.createNamedQuery("Person.findByDob"); 
        query.setParameter("dob", dob);
        return query.getResultList();
    }
    
    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Person> findByAddress(@PathParam("address") String address) {
        Query query = em.createNamedQuery("Person.findByAddress"); 
        query.setParameter("address", address);
        return query.getResultList();
    }
    
    @GET
    @Path("findByStateau/{stateau}")
    @Produces({"application/json"})
    public List<Person> findByStateau(@PathParam("stateau") String stateau) {
        Query query = em.createNamedQuery("Person.findByStateau"); 
        query.setParameter("stateau", stateau);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Person> findByPostcode(@PathParam("postcode") int postcode) {
        Query query = em.createNamedQuery("Person.findByPostcode"); 
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    // question3.b done.
    
    @GET
    @Path("findGenderSpeceficUserBasedOnSuburb/{stateau}/{postcode}/{gender}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> findGenderSpecificUserBasedOnSuburb(@PathParam("stateau") String stateau
	    , @PathParam("postcode") int postcode
	    , @PathParam("gender") String gender) {
	TypedQuery<Person> query = em.createQuery("SELECT p from Person p "
		+ "WHERE UPPER(p.stateau) = UPPER(:stateau) "
		+ "AND p.postcode = :postcode "
		+ "AND p.gender = :gender", Person.class);
	char c = gender.charAt(0);
	query.setParameter("stateau", stateau);
	query.setParameter("postcode", postcode);
	query.setParameter("gender", c);
    	return query.getResultList();	
    }

    @GET
    @Path("maxID")
    @Produces({MediaType.APPLICATION_JSON})
    public Object maxID() {
    List<Object[]> queryList = em.createQuery("SELECT p.personid, p.firstname FROM Person p ORDER BY p.personid DESC", Object[].class)
	    .setMaxResults(1)
            .getResultList();
	    
	
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
        for (Object[] row: queryList){
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("ID", (Integer)row[0])
		    .add("Name", (String)row[1])
		    .build();
	    
            arrayBuilder.add(memoirObject);
    
    
    
    
	}
	JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    
    }
//    @GET
//    @Path("answer4")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Object approach2(){
//        List<Object[]> queryList = em.createQuery("SELECT p.firstname,p.surname,p.address from Person p", Object[].class).getResultList();
//        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//        for (Object[] row: queryList){
//            JsonObject personObject = Json.createObjectBuilder().add("firstName", (String)row[0]).add("surname", (String)row[1]).add("address", (String)row[2]).build();
//            arrayBuilder.add(personObject);
//        }
//        JsonArray jArray = arrayBuilder.build();
//        return jArray;
//    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
