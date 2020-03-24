/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import DAO.CommunityDao;
import DAO.ICommunityDao;
import DTO.Community;
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
@Path("Community")
public class CommunityResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CommunityResource
     */
    private ICommunityDao db = new CommunityDao("repos");

    public CommunityResource() {
    }

    private JSONObject convertCommunityToJson(Community c) {
        JSONObject jObj = new JSONObject();
        jObj.put("userId", c.getUserId());
        jObj.put("communityId", c.getCommunityId());
        jObj.put("communityName", c.getCommunityName());
        jObj.put("date", c.getDate());
        jObj.put("status", c.getStatus());
        return jObj;
    }

    /**
     * Retrieves representation of an instance of REST.CommunityResource
     *
     * @return an instance of java.lang.String
     */
//     public String getAllPosts() {
//        IPostDao DB = new PostDao("repos");
//
//        System.out.println("GET called: getAllPosts");
//
//        JSONArray array = new JSONArray();
//        try {
//            for (Post p : DB.getAllPosts()) {
//                JSONObject obj = convertPostToJson(p);
//                array.add(obj);
//            }
//            JSONObject response = new JSONObject();
//            response.put("Posts", array);
//        } catch (Exception e) {
//            //e.printStackTrace();
//            // This exception sends error message to client
//            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
//        }
//
//        return array.toJSONString();
    //return response.toJSONString();
    @GET
    @Path("/getAllCommunity")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllCommunitys() {

        System.out.println("GET called: getAllCommunity");

        JSONArray array = new JSONArray();
        try {
            for (Community c : db.getAllCommunity()) {
                JSONObject obj = convertCommunityToJson(c);
                array.add(obj);
                JSONObject response = new JSONObject();
                response.put("Community", array);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }

        return array.toJSONString();
    }

    @GET
    @Path("/communityByUser/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCommunityByUser(@PathParam("userId") int userId) {

        System.out.println("GET called: getCommentsOfUser");
        JSONArray array = new JSONArray();
        try {
            List<Community> community = db.getCommunityByUser(userId);
            if (community.isEmpty()) {
                return "{\"message\":\"No community found.\"}";
            } else {
                community.stream().map((c) -> convertCommunityToJson(c)).forEachOrdered((obj) -> {
                    array.add(obj);
                });
            }
            JSONObject response = new JSONObject();
            response.put("Community", array);
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
    public boolean CreateCommunity(String content) {
        boolean flag = false;
        System.out.println("POST content = " + content);
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            int userId = ((Long) obj.get("userId")).intValue();
            String communityContent = (String) obj.get("content");
            if (communityContent != null) {
                if (!communityContent.isEmpty()) {

                    flag = db.createCommunity(userId, communityContent);
                }
            }
        } catch (ParseException e) {
            System.out.println("Exception is Comment POST : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }

    /**
     * PUT method for updating or creating an instance of CommunityResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("/deleteCommunity/{communityId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean deletePost(@PathParam("loginDetails") int communityId) {
        System.out.println("'DELETE' content = " + communityId);
        boolean flag = false;
        try {
            if (communityId != -1) {
                System.out.println("community received in DELETE message = " + communityId);

                flag = db.deleteCommunity(communityId);
            }
        } catch (Exception e) {
            System.out.println("Exception is Topic DELETE : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }

    //update community
    @PUT
    @Path("/updateCommunity/{communityId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public boolean deletePost(@PathParam("loginDetails") int communityId, String communityName) {
        System.out.println("'UPDATE' content = " + communityId + " " + communityName);
        boolean flag = false;
        try {
            if (communityId != -1 && communityName != null) {
                System.out.println("community received in UPDATE message = " + communityId);

                flag = db.updateCommunity(communityId, communityName);
            }
        } catch (Exception e) {
            System.out.println("Exception is Topic UPDATE : " + e.getMessage());
            // This exception sends error message to client
            throw new javax.ws.rs.ServerErrorException(e.getMessage(), 500);
        }
        return flag;
    }
}
