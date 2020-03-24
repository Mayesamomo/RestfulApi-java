/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import DAO.IUserDao;
import DAO.UserDao;
import static DTO.Role.ADMIN;
import static DTO.Role.MODERATOR;
import static DTO.Role.USER;
import DTO.User;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * REST Web Service
 *
 * @author micha
 */
@Path("User")
public class UserResource {

    @Context
    private UriInfo context;
 private HttpServletResponse response;
    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    /**
     * below methods deals with exchange JSON object between database
     *
     */
    private User convertJsonStringToUser(String jsonString) {
        try {
            User u;
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            u = new User();
            u.setUsername((String) obj.get("username"));
            u.setEmail((String) obj.get("email"));
            return u;
        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private JSONObject convertUserToJson(User u) {
        JSONObject jObj = new JSONObject();
        jObj.put("userId", u.getUserId());
        jObj.put("profileId", u.getProfileId());
        jObj.put("userType", u.getUserType());
        jObj.put("fullName", u.getFullName());
        jObj.put("username", u.getUsername());
        jObj.put("error", "");
        return jObj;
    }

    /**
     * Retrieves representation of an instance of REST.UserResource
     *
     * @return an instance of java.lang.String
     */
    //Login method
    @POST
    @Path("/login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    // @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response login(String content) {
        User u = new User();
        try {
            System.out.println("GET content = " + content);
            IUserDao uDAO = new UserDao("repos");
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            String userName = (String) obj.get("username");
            String password = (String) obj.get("password");
            System.out.println(userName + " " + password);
            if (userName == null && password == null) {
                return Response.status(204).entity("Please enter username and password !!").build();
            }
            if (!uDAO.login(userName, password) && password.length() >= 6 && password.length() <= 16 && (u.getUserType() != USER || u.getUserType() != ADMIN || u.getUserType() != MODERATOR)) {
                //System.out.println(u.toString());
                return Response.status(201).entity("Wrong username or password !!").build();
            }else{
                  uDAO.login(userName, password);
                  
                  return Response.status(200).entity("LoggedIn!").build();
            }
          
            
        } catch (Exception e) {
            System.out.println("Exception is User GET : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        //return Response.status(500).entity("Server error. Try again!").build();
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content) {
    }

    //register meetthod
    @POST
    @Path("/register")
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String content) {
        boolean flag = false;
        System.out.println("POST content = " + content);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            String fullName = ((String) obj.get("fullName"));
            String userName = ((String) obj.get("username"));
            String email = ((String) obj.get("email"));
            String password = ((String) obj.get("password"));

//checking if the user input is empty it stops  
            if (fullName == null && userName == null && password == null && email == null) {

                return Response.status(400).entity("Please enter all details !!").build();
            }
            if (!fullName.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && password.length() >= 6 && password.length() <= 16) {
                IUserDao db = new UserDao("repos");
                db.register(fullName, userName, password, email);
                return Response.status(200).entity("Account created!").build();
            }
        } catch (Exception e) {
            System.out.println("Exception is User POST : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return null;

    }
}
