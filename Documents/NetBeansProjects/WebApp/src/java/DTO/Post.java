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
public class Post {

    private int postId;
    private int userId;
    private String postTitle;
    private String postDesc;
    private String postDate;
    private String media;
    private int status;
    private String username;
    public String CommunityName;

    //constructor 
    public Post() {
    }

    public Post(int postId, int userId, String postTitle, String postDesc, String postDate, String media, int status, String username, String CommunityName) {
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.postDate = postDate;
        this.media = media;
        this.status = status;
        this.username = username;
        this.CommunityName = CommunityName;
    }

    public Post(String postTitle, String postDesc, String postDate, String media, String username, String CommunityName) {
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.postDate = postDate;
        this.media = media;
        this.username = username;
        this.CommunityName = CommunityName;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getMedia() {
        return media;
    }

    public int getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getCommunityName() {
        return CommunityName;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommunityName(String CommunityName) {
        this.CommunityName = CommunityName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.postId;
        hash = 11 * hash + this.userId;
        hash = 11 * hash + Objects.hashCode(this.postTitle);
        hash = 11 * hash + Objects.hashCode(this.postDesc);
        hash = 11 * hash + Objects.hashCode(this.postDate);
        hash = 11 * hash + Objects.hashCode(this.media);
        hash = 11 * hash + this.status;
        hash = 11 * hash + Objects.hashCode(this.username);
        hash = 11 * hash + Objects.hashCode(this.CommunityName);
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
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.postTitle, other.postTitle)) {
            return false;
        }
        if (!Objects.equals(this.postDesc, other.postDesc)) {
            return false;
        }
        if (!Objects.equals(this.postDate, other.postDate)) {
            return false;
        }
        if (!Objects.equals(this.media, other.media)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.CommunityName, other.CommunityName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", userId=" + userId + ", postTitle=" + postTitle + ", postDesc=" + postDesc + ", postDate=" + postDate + ", media=" + media + ", status=" + status + ", username=" + username + ", CommunityName=" + CommunityName + '}';
    }



   

   

}
