/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Objects;

/**
 *
 * @author micha
 */
public class Comment {

    private int userId;
    private int postID;
    private int commentID;
    private String username;
    private String commentDesc;
    private String date;
    private int status;
    
    //constructor

    public Comment() {
    }

    public Comment(int userId, int postID, int commentID, String username, String commentDesc, String date, int status) {
        this.userId = userId;
        this.postID = postID;
        this.commentID = commentID;
        this.username = username;
        this.commentDesc = commentDesc;
        this.date = date;
        this.status = status;
    }
 public Comment( int postID, int commentID,  int status) {
       
        this.postID = postID;
        this.commentID = commentID;
        this.status = status;
    }
    public int getUserId() {
        return userId;
    }

    public int getPostID() {
        return postID;
    }

    public int getCommentID() {
        return commentID;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.userId;
        hash = 83 * hash + this.postID;
        hash = 83 * hash + this.commentID;
        hash = 83 * hash + Objects.hashCode(this.username);
        hash = 83 * hash + Objects.hashCode(this.commentDesc);
        hash = 83 * hash + Objects.hashCode(this.date);
        hash = 83 * hash + this.status;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.postID != other.postID) {
            return false;
        }
        if (this.commentID != other.commentID) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.commentDesc, other.commentDesc)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }

    @Override
    public String toString() {
        return "Comment{" + "userId=" + userId + ", postID=" + postID + ", commentID=" + commentID + ", username=" + username + ", commentDesc=" + commentDesc + ", date=" + date + ", status=" + status + '}';
    }
    
   
    
}
