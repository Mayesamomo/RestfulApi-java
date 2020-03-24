/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.User;

/**
 *
 * @author micha
 */
public interface IUserDao {

    public boolean register(String fullname,String userName, String password, String email);

     public boolean login(String user_name, String password);
     public boolean checkIfExist(int userId);
}
