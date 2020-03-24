/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Community;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author micha
 */
public class CommunityDao extends Dao implements ICommunityDao {

    public CommunityDao(String database) {
        super(database);
    }

    @Override
    public ArrayList<Community> getAllCommunitys() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Community> community = new ArrayList();
        try {
            con = getConnection();
            String query = "select * from community where status =1";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Community comm = new Community(
                        rs.getInt("communityId"),
                        rs.getString("username"),
                        rs.getString("communityName"),
                        rs.getString("date"),
                        rs.getInt("status"));
                community.add(comm);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUser(Users use) method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the   method: " + e.getMessage());
            }
        }
        return community;
    }

    @Override
    public ArrayList<Community> getCommunityByUser(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Community> community = new ArrayList();
        try {
            con = getConnection();
            String query = "select u.username, c.communityId, c.userId, c.communityName,c.date,c.status from community c INNER join users u ON u.userId=c.userId WHERE c.userId =? AND c.status =1";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Community comm = new Community(
                        rs.getInt("communityId"),
                        rs.getString("username"),
                        rs.getString("communityName"),
                        rs.getString("date"),
                        rs.getInt("status"));

                community.add(comm);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUser(Users use) method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the   method: " + e.getMessage());
            }
        }
        return community;
    }

    @Override
    public ArrayList<Community> getCommunityById(int communityId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Community> community = new ArrayList();
        try {
            con = getConnection();
            String query = "SELECT u.username, c.communityId, c.userId, c.communityName, c.date, c.status FROM community c INNER JOIN users u ON u.userId = c.userId WHERE c.communityId =? AND c.status = 1";
            ps = con.prepareStatement(query);
            ps.setInt(1, communityId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Community comm = new Community(
                        rs.getInt("communityId"),
                        rs.getString("username"),
                        rs.getString("communityName"),
                        rs.getString("date"),
                        rs.getInt("status"));

                community.add(comm);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getUser(Users use) method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the   method: " + e.getMessage());
            }
        }
        return community;
    }

    @Override
    public boolean createCommunity(int userId, String communityName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag;
        try {
            conn = getConnection();
            // Can also do:
            String query = "INSERT INTO community( userId, communityName) VALUES (?,?)";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, communityName);
            ps.executeUpdate();
            flag = true;
            return flag;

        } catch (SQLException se) {
            System.out.println("SQL Exception occurred: " + se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the result set, statement and the connection
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Exception occurred when attempting to close the PreparedStatement: " + ex.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Exception occurred when attempting to close the Connection: " + ex.getMessage());
                }
            }
        }

        flag = false;
        return flag;
    }

    @Override
    public boolean updateCommunity(int communityId, String communityName) {
       Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;       
        try
        {
            con = getConnection();         
            ps = con.prepareStatement("UPDATE community SET communityName = ? WHERE communityId= ?");
            ps.setString(1, communityName);
            ps.setInt(2, communityId);
            ps.executeUpdate();
            System.out.println("Comment has been updated.");
            flag = true;
        }catch (SQLException e) {
            System.out.println("Exception occured in the updateComment() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the updateComment() method: " + e.getMessage());
            }
        }
        return flag;
    }
    @Override
    public boolean deleteCommunity(int communityId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;       
        try
        {
            con = getConnection();         
            ps = con.prepareStatement("UPDATE community SET status = 0 WHERE communityId = ?");
            ps.setInt(1, communityId);
            ps.executeUpdate();
            System.out.println("Comment has been deleted.");
            flag = true;
        }catch (SQLException e) {
            System.out.println("Exception occured in the deletePost() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the deletePost() method: " + e.getMessage());
            }
        }
        return flag;
    }
    public static void main(String[] args) {
        ICommunityDao da = new CommunityDao("repos");
        //Community co = new Community(35, );
        System.out.println(da.deleteCommunity(13));
    }
}
