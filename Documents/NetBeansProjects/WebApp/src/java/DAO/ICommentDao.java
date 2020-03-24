/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Comment;
import java.util.List;

/**
 *
 * @author micha
 */
public interface ICommentDao {

    public List<Comment> getAllComments();

    public List<Comment> getCommentsOfUser(int userId);

    public List<Comment> getCommentsOfPost(int postId);

    public boolean makeComment(int userId, int postId, String desc);

    public boolean deleteComment(int commentID);

    public boolean updateComment(int commentID, String desc);
}
