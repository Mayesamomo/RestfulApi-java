/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

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

    @Context
    private UriInfo context;
    protected ICommentDao commentDB = new CommentDao("repos");

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
     * Retrieves representation of an instance of REST.CommentResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllComments() {
        System.out.println("GET called: getAllComments");

        JSONArray array = new JSONArray();
        try {
            commentDB.getAllComments().stream().map((c) -> convertCommentToJson(c)).forEachOrdered((obj) -> {
                array.add(obj);
            });
            JSONObject response = new JSONObject();
            response.put("Comments", array);
        } catch (Exception e) {
            //e.printStackTrace();
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }

    @GET
    @Path("/commentsByUser/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsOfUser(@PathParam("userId") int userId) {

        System.out.println("GET called: getCommentsOfUser");
        JSONArray array = new JSONArray();
        try {
            List<Comment> comments = commentDB.getCommentsOfUser(userId);
            if (comments.isEmpty()) {
                return "{\"message\":\"No comments found.\"}";
            } else {
                comments.stream().map((c) -> convertCommentToJson(c)).forEachOrdered((obj) -> {
                    array.add(obj);
                });
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
    @Path("/commentsByPost/{postId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommentsOfPost(@PathParam("postId") int postId) {

        System.out.println("GET called: GetCommentsOfPost");
        JSONArray array = new JSONArray();
        try {
            List<Comment> comments = commentDB.getCommentsOfPost(postId);
            if (comments.isEmpty()) {
                return "{\"message\":\"No Comments found\"}";
            } else {
                comments.stream().map((c) -> convertCommentToJson(c)).forEachOrdered((obj) -> {
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
//count comment by postId
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/countComments{postId}")
    public int countComment(@PathParam("postId")int postId) {
        int output = 0;
        //int user_typeNum = Integer.parseInt(postId);
        try {
            output = commentDB.countCommentOfPost(postId);
            
        } catch (Exception e) {
            System.out.println("Exception is Comments  : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);

        }

        return output;
    }
    
    
    @POST
     @Path("/commentsByUser/{userId}")
    //@Consumes(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean makeComment(String content) {
        boolean flag = false;
        System.out.println("POST content = " + content);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            int userId = ((Long) obj.get("userId")).intValue();
            int postID = ((Long) obj.get("postId")).intValue();
            String commentContent = (String) obj.get("content");
            if (commentContent != null) {
                if (!commentContent.isEmpty()) {

                    flag = commentDB.makeComment(userId, postID, commentContent);
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
    public boolean deleteComment(@PathParam("id") int commentID, String content) {
        System.out.println("'DELETE' content = " + commentID);
        boolean flag = false;
        try {

            flag = commentDB.deleteComment(commentID);
        } catch (Exception e) {
            System.out.println("Exception is Post DELETE : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }

    /**
     * PUT method for updating or creating an instance of CommentResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("/updateComment/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean updateComment(@PathParam("id")String content) {
        throw new UnsupportedOperationException();
    }
}
