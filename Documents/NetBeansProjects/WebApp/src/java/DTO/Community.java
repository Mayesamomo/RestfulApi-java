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
public class Community {
    private int communityId;
    private int userId;
    private String username;
    private String communityName;
    private String date;
    private int status;

    public Community() {
    }

    public Community(int communityId, String username, String communityName, String date, int status) {
        this.communityId = communityId;
        this.username = username;
        this.communityName = communityName;
        this.date = date;
        this.status = status;
    }

    public Community(int communityId, int userId, String communityName, String date, int status) {
        this.communityId = communityId;
        this.userId = userId;
        this.communityName = communityName;
        this.date = date;
        this.status = status;
    }

    public Community(int userId, String communityName) {
        this.userId = userId;
        this.communityName = communityName;
    }

    public int getCommunityId() {
        return communityId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getCommunityName() {
        return communityName;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.communityId;
        hash = 17 * hash + this.userId;
        hash = 17 * hash + Objects.hashCode(this.username);
        hash = 17 * hash + Objects.hashCode(this.communityName);
        hash = 17 * hash + Objects.hashCode(this.date);
        hash = 17 * hash + this.status;
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
        final Community other = (Community) obj;
        if (this.communityId != other.communityId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.communityName, other.communityName)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Community{" + "communityId=" + communityId + ", userId=" + userId + ", username=" + username + ", communityName=" + communityName + ", date=" + date + ", status=" + status + '}';
    }

    
    
    
    
}
