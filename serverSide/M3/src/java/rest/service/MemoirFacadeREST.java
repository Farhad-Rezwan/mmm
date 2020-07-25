/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import rest.Memoir;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author farhadullahrezwan
 */
@Stateless
@Path("rest/memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "M3PU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
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
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByMoviename/{moviename}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByMoviename(@PathParam("moviename") String moviename){
        Query query = em.createNamedQuery("Memoir.findByMoviename");
        query.setParameter("moviename", moviename);
        return query.getResultList();
    }
    
    @GET
    @Path("findByMoviereleasedate/{moviereleasedate}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByMoviereleasedate(@PathParam("moviereleasedate") Date moviereleasedate){
        Query query = em.createNamedQuery("Memoir.findByMoviereleasedate");
        query.setParameter("moviereleasedate", moviereleasedate);
        return query.getResultList();
    }
   
    @GET
    @Path("findByDatetimewatched/{datetimewatched}")
    @Produces ({MediaType.APPLICATION_JSON})
    public List<Memoir> findByDateTimeWatched(@PathParam("datetimewatched") Timestamp datetimewatched){
        Query query = em.createNamedQuery("Memoir.findByDatetimewatched");
        query.setParameter("datetimewatched", datetimewatched);
        return query.getResultList();
        
    }
    
    @GET
    @Path("findByComment/{comment}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByComment(@PathParam("comment") String comment){
        Query query = em.createNamedQuery("Memoir.findByComment");
        query.setParameter("comment", comment);
        return query.getResultList();
    }
    
    @GET
    @Path("findByRating/{rating}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByRating(@PathParam("rating") float rating){
        Query query = em.createNamedQuery("Memoir.findByRating");
        query.setParameter("rating", rating);
        return query.getResultList();
    }
    
    @GET
    @Path("findByCinemaID/{cinemaid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByCinemaID(@PathParam("cinemaid") Integer cinemaid) {
	Query query = em.createNamedQuery("Memoir.findByCinemaID");
	query.setParameter("cinemaid", cinemaid);
	return query.getResultList();
    }

    @GET
    @Path("findByPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByPersonID(@PathParam("personid") Integer personid){
    	Query query = em.createNamedQuery("Memoir.findByPersonID");
	query.setParameter("personid", personid);
	return query.getResultList();
    }
    
    // question3.c done.
    
    @GET
    @Path("findMemoirByMovieAndCinemaName/{moviename}/{cinemaname}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findMemoirByMovieAndCinemaName(@PathParam("moviename") String moviename
	    , @PathParam("cinemaname") String cinemaname){
        TypedQuery<Memoir> query = em.createQuery("SELECT m FROM Memoir m "
		+ "WHERE m.moviename = :moviename "
		+ "AND m.cinemaid.cinemaname = :cinemaname", Memoir.class);
	
	query.setParameter("moviename", moviename);
        query.setParameter("cinemaname", cinemaname);
	
        return query.getResultList();
    }
    
   // question3.d done.
    
    @GET
    @Path("findMemoirBasedOnCinemaNameandRatingAboveRating/{cinemaname}/{rating}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findByMovieAndCinemaName(@PathParam("cinemaname") String cinemaname, @PathParam("rating") float rating){
        Query query = em.createNamedQuery("Memoir.findMemoirBasedOnCinemaNameandRatingAboveRating");
        query.setParameter("cinemaname", cinemaname);
        query.setParameter("rating", rating);
        return query.getResultList();
    }
//    Answer 4.a)

    @GET
    @Path("findCSuburbByPersonIDandDateRange/{personid}/{startingdate}/{endingdate}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object getCSuburbByPersonIDandDateRange(@PathParam("personid") int personid
	    , @PathParam("startingdate") String startingdate
	    , @PathParam("endingdate") String endingdate) throws ParseException {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
        java.util.Date parsedS = format.parse(startingdate);
        java.util.Date parsedE = format.parse(endingdate);
        java.sql.Date startingDate = new java.sql.Date(parsedS.getTime());  
        java.sql.Date endingDate = new java.sql.Date(parsedE.getTime()); 
	
        List<Object[]> queryList = em.createQuery("SELECT m.cinemaid.cinemasuburb, count(m.memoirid) "
		+ "FROM Memoir m "
		+ "WHERE m.personid.personid = :personid AND (:startingDate <= m.datetimewatched AND m.datetimewatched <= :endingDate) "
		+ "GROUP By m.cinemaid.cinemasuburb", Object[].class)
            .setParameter("personid", personid)
            .setParameter("startingDate", startingDate)
            .setParameter("endingDate", endingDate)
            .getResultList();
	    
	
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
        for (Object[] row: queryList){
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("cinemaSuburb", (String)row[0])
		    .add("countMoviesWatched", (Long)row[1])
		    .build();
	    
            arrayBuilder.add(memoirObject);
        }
	
        JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    }


//    Answer 4.b)
    
    @GET
    @Path("findMovieWachedPerMonthGivenPersonIDandYear/{personid}/{year}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findMovieWachedPerMonthGivenPersonIDandYear(@PathParam("personid") int personid
	    , @PathParam("year") int year){
        List<Object[]> queryList = em.createQuery("SELECT (EXTRACT(MONTH m.datetimewatched)) as mon, count(m.personid.personid) "
		+ "FROM Memoir m "
		+ "WHERE m.personid.personid = :personid AND EXTRACT(Year (cast(m.datetimewatched as Date))) = :year "
		+ "GROUP BY mon, m.personid.personid", Object[].class)
		.setParameter("personid", personid)
                .setParameter("year", year)
                .getResultList();
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ArrayList<String> months = new ArrayList<String>();
	months.add("January");
	months.add("February");
	months.add("March");
	months.add("April");
	months.add("May");
	months.add("June");
	months.add("July");
	months.add("August");
	months.add("September");
	months.add("October");
	months.add("November");  
	months.add("December");
	
        for (Object[] row: queryList){
            JsonObject memoirObject;
            memoirObject = Json.createObjectBuilder()
		    .add("month", (String)months.get((int) row[0] - 1))
		    .add("countMovieWatched", (Long)row[1])
		    .build();
	    
            arrayBuilder.add(memoirObject);
        }
	
        JsonArray jArray = arrayBuilder.build();
	
        return jArray;
	
      } 

    /*
	Answer c)  
    */
    
    @GET
    @Path("findHighestRatedMovieWithRDateandRatingByPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findHighestRatedMovieWithRDateandRatingByPersonID(@PathParam("personid") int personid) {
        List<Object[]> queryList = em.createQuery("SELECT m.moviename, m.rating, m.moviereleasedate "
		+ "FROM Memoir m "
		+ "WHERE m.personid.personid = :personid "
		+ "AND m.rating = ("
		+ "SELECT max(k.rating) "
		+ "FROM "
		+ "Memoir k "
		+ "WHERE "
		+ "k.personid.personid = :personid)", Object[].class)
            .setParameter("personid", personid)
            .getResultList();
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	
        for (Object[] row: queryList){
	    String value = dateFormat.format(row[2]); 
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("movieName", (String)row[0])
		    .add("ratings", (BigDecimal)row[1])
		    .add("releaseDate", (String)value)
		    .build();
            arrayBuilder.add(memoirObject);
        }
	
        JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    }

    /*
	Answer d)  
    */

    @GET
    @Path("findMovieWithSameReleaseAndWatchingDateGivenPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object findMovieWithSameReleaseAndWatchingDateGivenPersonID(@PathParam("personid") int personid){
        List<Object[]> queryList = em.createQuery("SELECT DISTINCT m.moviename, m.moviereleasedate, m.rating"
		+ "FROM Memoir m "
		+ "WHERE m.personid.personid = :personid "
		+ "AND EXTRACT(Year (cast(m.datetimewatched as Date))) = EXTRACT(Year (cast(m.moviereleasedate as Date)))", Object[].class)
		
            .setParameter("personid", personid)
            .getResultList();
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy");
	
        for (Object[] row: queryList){
	    String value = dateFormat.format(row[1]); 
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("moviename", (String)row[0])
		    .add("releaseYear", (String)value)
		    .add("rating", (Double) row[2])
		    .build();
            arrayBuilder.add(memoirObject);
        }
	
        JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    }



    /*
	Answer e)  
    */
    
    @GET
    @Path("findRemakeMoviesWatchedGivenPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object answerE(@PathParam("personid") int personid){
        List<Object[]> queryList = em.createQuery("SELECT DISTINCT m.moviename, m.moviereleasedate "
		+ "FROM Memoir m JOIN Memoir p on m.moviename = p.moviename "
		+ "WHERE m.personid.personid = :personid AND m.moviereleasedate != p.moviereleasedate", Object[].class)
            .setParameter("personid", personid)
            .getResultList();
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy");

        for (Object[] row: queryList){
	    String value = dateFormat.format(row[1]); 
	    JsonObject memoirObject = Json.createObjectBuilder()
		    .add("movieName", (String)row[0])
		    .add("releaseYear", (String)value).build();
            arrayBuilder.add(memoirObject);
	}
            
	
        JsonArray jArray = arrayBuilder.build();
	
        return jArray;
    }

    /*
	Answer f)  
    */

    @GET
    @Path("findRecentHiestRatedMovieGivenPersonID/{personid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Object answerF(@PathParam("personid") int personid){
	int recentYear = Calendar.getInstance().get(Calendar.YEAR);;
        List<Object[]> queryList = em.createQuery("SELECT  m.moviename, MAX(m.rating) as rate, m.moviereleasedate "
		+ "FROM Memoir m "
		+ "WHERE m.personid.personid = :personid AND EXTRACT(Year (cast(m.moviereleasedate as Date))) = :recentYear "
		+ "GROUP BY m.moviename, m.moviereleasedate "
		+ "ORDER BY rate DESC", Object[].class)
            .setParameter("personid", personid)
	    .setParameter("recentYear", recentYear)
	    .setMaxResults(5)
            .getResultList();
	
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	
        for (Object[] row: queryList){
	    String value = dateFormat.format(row[2]); 
            JsonObject memoirObject = Json.createObjectBuilder()
		    .add("movieName", (String)row[0])
		    .add("ratingScore", (BigDecimal)row[1])
		    .add("releaseDate", (String)value)
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
