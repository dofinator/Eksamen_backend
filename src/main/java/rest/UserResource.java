package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.UserDTO;
import entities.User;
import facades.UserFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
//import utils.SetupTestUsers;

/**
 * @author lam@cphbusiness.dk
 */
@Path("users")
public class UserResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String cachedResponse;
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("hotels")
    public String allHotels() throws InterruptedException, ExecutionException, TimeoutException {
        String result = fetcher.HotelFetcher.fetchAllHotels(ES, GSON);
        cachedResponse = result;
        return result;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("hotel/{id}")
    public String getHotelById(@PathParam("id") String id) throws InterruptedException, ExecutionException, TimeoutException {
        String result = fetcher.HotelFetcher.fetchHotelById(ES, GSON, id);
        cachedResponse = result;
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    @Path("register")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(String user) {
        UserDTO u = GSON.fromJson(user, UserDTO.class);
        u = FACADE.registerUser(u);
        return Response.ok(u).build();
    }
    
//    @Path("setUpUsers")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public void setUpUsers() {
//        SetupTestUsers.setUpUsers();
//    }
    
    
    
    
}