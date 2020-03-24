/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author micha
 */
public class Vote {
    private int voteId;
    private int userId;
    private int postId;
    private int voteUp;
    private int voteDown;

    public Vote(int voteId, int userId, int postId, int voteUp, int voteDown) {
        this.voteId = voteId;
        this.userId = userId;
        this.postId = postId;
        this.voteUp = voteUp;
        this.voteDown = voteDown;
    }

    public int getVoteId() {
        return voteId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.voteId;
        hash = 53 * hash + this.userId;
        hash = 53 * hash + this.postId;
        hash = 53 * hash + this.voteUp;
        hash = 53 * hash + this.voteDown;
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
        final Vote other = (Vote) obj;
        if (this.voteId != other.voteId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.postId != other.postId) {
            return false;
        }
        if (this.voteUp != other.voteUp) {
            return false;
        }
        return this.voteDown == other.voteDown;
    }

    @Override
    public String toString() {
        return "Vote{" + "voteId=" + voteId + ", userId=" + userId + ", postId=" + postId + ", voteUp=" + voteUp + ", voteDown=" + voteDown + '}';
    }
    
    
}
