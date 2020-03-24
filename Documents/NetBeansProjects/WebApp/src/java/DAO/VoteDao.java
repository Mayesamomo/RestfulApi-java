/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author micha
 */
public class VoteDao extends Dao implements IVoteDao {

    public VoteDao(String database) {
        super(database);
    }

    @Override
    public int getVoteUp() {
       Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try{
            con = getConnection();

            String query = "Select u.userName, c.userId, c.postID, c.commentID, c.content, c.commentDate, c.active from comments c inner join users u on u.userId = c.userId where active = 1"; 
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(); 
            
            while(rs.next())
            {
                
            }
        }catch (SQLException e) {
            System.out.println("Exception occured in the getAllComments() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllComments() method: " + e.getMessage());
            }
        }
        
        return result;
    }

    @Override
    public int getVoteDown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
