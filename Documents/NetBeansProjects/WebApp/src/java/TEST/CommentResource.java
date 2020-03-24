/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import DAO.CommentDao;
import DAO.ICommentDao;
import DTO.Comment;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author micha
 */
@Path("Comment")
public class CommentResource {
//connect to the database
    protected  ICommentDao DB = new CommentDao("repos");
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CommentResource
     */
    public CommentResource() {
    }
 private JSONObject convertCommentToJson(Comment c) {
        JSONObject jObj = new JSONObject();
        jObj.put("userId", c.getUserId());
        jObj.put("postId", c.getPostID());
        jObj.put("commentID", c.getCommentID());
        jObj.put("username", c.getUsername());
        jObj.put("comment_desc", c.getCommentDesc());
        jObj.put("commentDate", c.getDate());
        jObj.put("status", c.getStatus());

        return jObj;    
    }
    /**
     * Retrieves representation of an instance of TEST.CommentResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllComments() {
        //ICommentDao DB = new CommentDao("repos");

        System.out.println("GET called: getAllComments");

        JSONArray array = new JSONArray();
        try {
            for (Comment c : DB.getAllComments()) {
                JSONObject obj = convertCommentToJson(c);
                array.add(obj);
            }
            JSONObject response = new JSONObject();
            response.put("Comments", array);
        } catch (Exception e) {
            //e.printStackTrace();
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }
    //get user''s comment
    @GET
    @Path("/commentsOfUser/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsOfUser(@PathParam("userId") int userId) {
       // ICommentDao DB = new CommentDao("repos");

        System.out.println("GET called: getCommentsOfUser");
        JSONArray array = new JSONArray();
        try{
            List<Comment> comments = DB.getCommentsOfUser(userId);
            if (comments.isEmpty()) 
            {               
                return "{\"message\":\"No comments found.\"}";
            } 
            else 
            {
                for (Comment c : comments) 
                {
                    JSONObject obj = convertCommentToJson(c);
                    array.add(obj);
                }
            }
            JSONObject response = new JSONObject();
            response.put("Comments", array);
        } catch (Exception e) {
            System.out.println(e);
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }

    
    @GET
    @Path("/commentsOfPost/{postId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsOfPost(@PathParam("postId") int postId) {
       // ICommentDao DB = new CommentDao("repos");

        System.out.println("GET called: GetCommentsOfPost");
        JSONArray array = new JSONArray();
        try{
            List<Comment> comments =   DB.getCommentsOfPost(postId);
            if (comments.isEmpty()) 
            {               
                return "{\"message\":\"No Comments found\"}";
            } 
            else 
            {
                for (Comment c : comments) 
                {
                    JSONObject obj = convertCommentToJson(c);
                    array.add(obj);
                }
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean makeComment(String content) {
        boolean flag = false;
        System.out.println("POST content = " + content);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            int userId = ((Long) obj.get("userId")).intValue();
            int postID = ((Long) obj.get("postId")).intValue();
            String comment_desc = (String) obj.get("comment_desc");
            if (comment_desc != null) {
                if (!comment_desc.isEmpty()) {
                    
                  // ICommentDao DB = new CommentDao("repos");
                    flag = DB.makeComment(userId, postID, comment_desc);
                }
            }
        } catch (ParseException e) {
            System.out.println("Exception is Comment POST : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }
    
     @PUT
    @Path("/deleteComment/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean deleteComment(@PathParam("id") int commentID, String content) {
        System.out.println("'DELETE' content = " + commentID);
        boolean flag = false;
        try {
               // ICommentDao DB = new CommentDao("repos");
                flag = DB.deleteComment(commentID);
        } catch (Exception e) {
            System.out.println("Exception is Post DELETE : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }
    /**
     * PUT method for updating or creating an instance of CommentResource
     * @param content representation for the resource
     */
   @PUT
    @Path("/updateComment")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean updateComment(String content) {
        throw new UnsupportedOperationException();
    }
}
