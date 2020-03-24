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
public enum Role {
    USER,
    ADMIN,
    MODERATOR;
    
    public static Role getUSER() {
        return USER;
    }

    public static Role getADMIN() {
        return ADMIN;
    }

    public static Role getMODERATOR() {
        return MODERATOR;
    }
}
