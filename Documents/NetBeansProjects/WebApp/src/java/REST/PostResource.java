/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import DAO.IPostDao;
import DAO.IUserDao;
import DAO.PostDao;
import DAO.UserDao;
import DTO.Post;
import DTO.User;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author micha
 */
@Path("Post")
public class PostResource {

    IUserDao Udb = new UserDao("repos");
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PostResource
     */
    public PostResource() {
    }

    public static JSONObject convertPostToJson(Post p) {
        JSONObject jObj = new JSONObject();
        jObj.put("postId", p.getPostId());
        jObj.put("userId", p.getUserId());
        jObj.put("postTitle", p.getPostTitle());
        jObj.put("postDesc", p.getPostDesc());
        jObj.put("postDate", p.getPostDate());
        jObj.put("media", p.getMedia());
        jObj.put("status", p.getStatus());
        jObj.put("username", p.getUsername());
        jObj.put("communityName", p.getCommunityName());

        return jObj;
    }

    /**
     * Retrieves representation of an instance of REST.PostResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("GetAllPost")
    @Produces(MediaType.TEXT_PLAIN)
    //@Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public String getAllPosts() {
        IPostDao DB = new PostDao("repos");
        System.out.println("GET called: getAllPosts");
        JSONArray array = new JSONArray();
        try {
            DB.getAllPosts().stream().map((p) -> convertPostToJson(p)).forEachOrdered((obj) -> {
                array.add(obj);
            });
            JSONObject response = new JSONObject();
            response.put("Posts", array);
        } catch (Exception e) {
            //e.printStackTrace();
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
        //return response.toJSONString();
    }

    @GET
    @Path("/singlePost/{postId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOnePost(@PathParam("postId") int postId) {

        IPostDao DB = new PostDao("repos");

        System.out.println("GET called: getOnePosts");
        JSONArray array = new JSONArray();
        try {
            DB.getOnePost(postId).stream().map((p) -> convertPostToJson(p)).forEachOrdered((obj) -> {
                array.add(obj);
            });
            JSONObject response = new JSONObject();
            response.put("Posts", array);
        } catch (Exception e) {
          
            // This exception sends server error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }

    @GET
    @Path("/userPosts/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPostsByUser(@PathParam("userId") int userId) {
        IPostDao DB = new PostDao("repos");

        System.out.println("GET called: getPostsByUser");
        JSONArray array = new JSONArray();
        try {
            List<Post> posts = DB.getPostsByUser(userId);
            if (posts.isEmpty()) {
                return "{\"message\":\"No posts found\"}";
            } else {
                posts.stream().map((p) -> convertPostToJson(p)).forEachOrdered((obj) -> {
                    array.add(obj);
                });
            }
            JSONObject response = new JSONObject();
            response.put("Posts", array);
        } catch (Exception e) {
            System.out.println(e);
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }
@GET
    @Path("/getPostByCommunityId/{communityId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPostByCommunityId(@PathParam("communityId") int communityId) {
        IPostDao DB = new PostDao("repos");

        System.out.println("GET called: getPostsByCommunity");
        JSONArray array = new JSONArray();
        try {
            List<Post> posts = DB.getAllPostByCommunityId(communityId);
            if (posts.isEmpty()) {
                return "{\"message\":\"No posts found\"}";
            } else {
                posts.stream().map((p) -> convertPostToJson(p)).forEachOrdered((obj) -> {
                    array.add(obj);
                });
            }
            JSONObject response = new JSONObject();
            response.put("Posts", array);
        } catch (Exception e) {
            System.out.println(e);
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }
    
    @POST
    // @Path("/CreatePost/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response makePost(String content) {
        boolean flag = false;

        System.out.println("POST content = " + content);
        try {
            User u = new User();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            int userId = ((Long) obj.get("userId")).intValue();
            String media = (String) obj.get("media");
            String postTitle = (String) obj.get("postTitle");
            String postDesc = (String) obj.get("postDesc");
            if (postTitle == null && postDesc == null) {
                return Response.status(201).entity("Please enter post title and post content!").build();
            }
            boolean exist = Udb.checkIfExist(userId); //check if the userId matches the one in databse
            if (!exist || userId <= 0) {
                return Response.status(401).entity("UnAthourized!").build();
            }
            if (postTitle != null && postDesc != null) {
                IPostDao db = new PostDao("repos");
                flag = db.makePost(userId, postTitle, postDesc, media);
                return Response.status(200).entity("post created!").build();
            }
        } catch (ParseException e) {
            System.out.println("Exception is User POST : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return Response.status(204).entity("can not make post").build();

    }

    @PUT
    @Path("/deletePost/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean deletePost(@PathParam("id") int postId, String content) {
        System.out.println("'DELETE' content = " + content);
        boolean flag = false;
        try {
            IPostDao DB = new PostDao("repos");
            flag = DB.deletePost(postId);
        } catch (Exception e) {
            System.out.println("Exception is Post DELETE : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }

    /**
     * PUT method for updating or creating an instance of PostResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("/updatePost/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePost(String content) {
        boolean flag = false;
        System.out.println("POST content = " + content);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            int postId = ((Long) obj.get("postId")).intValue();
            String media = (String) obj.get("media");
            String postTitle = (String) obj.get("postTitle");
            String postDesc = (String) obj.get("postDesc");
            if (postTitle == null && postDesc == null) {
                return Response.status(201).entity("Please enter post title and post content !!").build();
            }
            if (postTitle != null && postDesc != null) {
                IPostDao db = new PostDao("repos");
                flag = db.updatePost(postId, postTitle, postDesc);
                return Response.status(200).entity("post updated!").build();
            }
        } catch (ParseException e) {
            System.out.println("Exception is User POST : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return Response.status(204).entity("can not update post").build();

    }
}
