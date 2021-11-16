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
public class User {

    private int userId;
    private int profileId;
    private String fullName;
    private String email;
    private String username;
    private Role userType;
    private String date;
    private int status;

    //empty constructor
    public User() {
    }

    public User(int userId, int profileId, String fullName, String email, String username, String userType, String date, int status) {
        this.userId = userId;
        this.profileId = profileId;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.userType = Role.valueOf(userType);
        this.date = date;
        this.status = status;
    }

    public User(int userId, String email, String username, Role userType, int status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.userType = userType;
        this.status = status;
    }

    public User(int userId, int profileId, String email, String username, Role userType) {
        this.userId = userId;
        this.profileId = profileId;
        this.email = email;
        this.username = username;
        this.userType = userType;
    }

    public User(int userId, int profileId, String userType, String username) {
        this.userId = userId;
        this.profileId = profileId;
        this.userType = Role.valueOf(userType);
        this.username = username;

    }

    public User(String fullName, String email, String username) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
    }

    public User(int userId, int profileId, String fullName, String email, int status, String userType, String username) {
        this.userId = userId;
        this.profileId = profileId;
        this.fullName = fullName;
        this.email = email;

        this.userType = Role.valueOf(userType);
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Role getUserType() {
        return userType;
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

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserType(Role userType) {
        this.userType = userType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.userId;
        hash = 29 * hash + this.profileId;
        hash = 29 * hash + Objects.hashCode(this.fullName);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.userType);
        hash = 29 * hash + Objects.hashCode(this.date);
        hash = 29 * hash + this.status;
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
        final User other = (User) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.profileId != other.profileId) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return this.userType == other.userType;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", profileId=" + profileId + ", fullName=" + fullName + ", email=" + email + ", username=" + username + ", userType=" + userType + ", date=" + date + ", status=" + status + '}';
    }

}
