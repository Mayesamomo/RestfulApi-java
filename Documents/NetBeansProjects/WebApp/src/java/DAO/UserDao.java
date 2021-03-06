/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author micha
 */
public class UserDao extends Dao implements IUserDao {

    public UserDao(String database) {
        super(database);
    }

    @Override
    public boolean register(String fullname, String username, String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;

        try {
            con = getConnection();
//checking if the user already exist in the databas 
            String query = "select email, username from users where email = ? OR username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, username);
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            System.out.println("Number of other users with email " + email + "OR Username " + username + " : " + count);
            if (count == 0)//returns 0, there is no duplicates of this username, therefore:password is hashed before storage
            {
                String hashedPassword = hashPassword(password);
                ps = con.prepareStatement("insert into users (username,fullName,email, password) values ( ?, ?, ?,?)");
                ps.setString(1, username);
                ps.setString(2, fullname);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                ps.executeUpdate();
                ps = con.prepareStatement("insert into userprofile (userId) VALUES ((select userId from users where email = ?));");
                ps.setString(1, email);
                ps.executeUpdate();
                System.out.println("User has been added.");
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the register() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the register() method: " + e.getMessage());
            }
        }
        return flag;
    }

    //login
    @Override
    public boolean login(String user_name, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();

            String query = "select username, password from users where username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, user_name);
            rs = ps.executeQuery();
            int count = 0;
            int userId = 0;
            String username = "";
            String storedPassword = "";
            while (rs.next()) {
                count++;
                username = rs.getString("username");
                //userId = rs.getInt("userId");
                storedPassword = rs.getString("password");
            }
            System.out.println("id:" + userId);
            System.out.println("count:" + count);
            if (count == 1)//returns  1, a user with this username, so password match is to be checked:
            {
                if (checkPassword(password, storedPassword) == true) {
                    String query2 = "SELECT userId, profileId, userType FROM userprofile WHERE userId = ? AND status =1";
                    ps = con.prepareStatement(query2);
                    ps.setInt(1, userId);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        User u = new User(rs.getInt("userId"), rs.getInt("profileId"), rs.getInt("userType"), username);
                        return true;
                    }
                } else {
                    System.out.println("Wrong password");
                    return false;
                }
            } else {
                System.out.println("wrong  username");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the login() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the login() method: " + e.getMessage());
            }
        }
        User u = new User();
        return true;
    }
//hash password before inserting into the database for security

    private String hashPassword(String password) {
        int workload = 13;
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password, salt);

        return (hashed_password);
    }
//comfirm the hashed password

    public static boolean checkPassword(String password, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password, stored_hash);

        return (password_verified);
    }

    @Override
    public boolean checkIfExist(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // ArrayList<User> users = new ArrayList();
        boolean flag;
        try {
            con = getConnection();
//checking to see if username already exist in the database
            String query = "select userId, username,email from users where userId =?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            //set a counter to loop through the available users in the database
            //int count = 0;
            if (rs.next()) {
                flag = true;
                return flag;
            } else {
                flag = false;
                return flag;
            }

        } catch (SQLException e) {
            System.out.println("Exception occured in the register() method: " + e.getMessage());
        }
        flag=false;
        return flag;
       
    }

    public static void main(String[] args) {
        IUserDao db = new UserDao("repos");
        System.out.println(db.checkIfExist(35));
        //System.out.println(db.register("John Doe", "John", "john@gmail.com", "password"));
        //System.out.println(db.login("Jane","password"));
    }
}
