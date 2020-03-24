/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Post;
import java.util.List;

/**
 *
 * @author micha
 */
public interface IPostDao {

    public List<Post> getAllPosts();

    public List<Post> getOnePost(int postId);

    public List<Post> getPostsByUser(int userID);

    public List<Post> getAllPostByCommunityId(int communityId);

    public boolean makePost(int userId, String postTitle, String postDesc, String media);

    public boolean deletePost(int postId);

    public boolean updatePost(int postId, String title, String desc);
}
