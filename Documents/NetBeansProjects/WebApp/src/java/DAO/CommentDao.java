/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author micha
 */
public class CommentDao extends Dao implements ICommentDao {

    public CommentDao(String database) {
        super(database);
    }

    @Override
    public List<Comment> getAllComments() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList();

        try {
            con = getConnection();

            String query = "select u.username, c.userId, c.postID, c.commentID, c.comment_desc, c.commentDate, c.status from comments c inner join users u on u.userId = c.userId where status = 1";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comment p = new Comment(rs.getInt("userId"),
                        rs.getInt("postID"),
                        rs.getInt("commentID"),
                        rs.getString("username"),
                        rs.getString("comment_desc"),
                        rs.getString("commentDate"),
                        rs.getInt("status"));
                comments.add(p);
            }
        } catch (SQLException e) {
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

        return comments;
    }

    @Override
    public List<Comment> getCommentsOfUser(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList();

        try {
            con = getConnection();
            String query = "select u.username, c.userId, c.postID, c.commentID, c.comment_desc, c.commentDate, c.status from comments c inner join users u on u.userId = c.userId where status = 1 && c.userId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comment p = new Comment(rs.getInt("userId"),
                        rs.getInt("postID"),
                        rs.getInt("commentID"),
                        rs.getString("username"),
                        rs.getString("comment_desc"),
                        rs.getString("commentDate"),
                        rs.getInt("status"));
                comments.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getCommentsOfUser() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getCommentsOfUser() method: " + e.getMessage());
            }
        }

        return comments;
    }

    @Override
    public List<Comment> getCommentsOfPost(int postId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList();

        try {
            con = getConnection();
            String query = "select u.username, c.userId, c.postID, c.commentID, c.comment_desc, c.commentDate, c.status from comments c inner join users u on u.userId = c.userId where status = 1 &&  postId = ? order by commentID desc";
            ps = con.prepareStatement(query);
            ps.setInt(1, postId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Comment p = new Comment(rs.getInt("userId"),
                        rs.getInt("postID"),
                        rs.getInt("commentID"),
                        rs.getString("username"),
                        rs.getString("comment_desc"),
                        rs.getString("commentDate"),
                        rs.getInt("status"));
                comments.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getCommentsOfUser() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getCommentsOfUser() method: " + e.getMessage());
            }
        }

        return comments;
    }

    @Override
    public boolean makeComment(int userId, int postId, String desc) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;

        try {
            con = getConnection();

            ps = con.prepareStatement("insert into comments (userId, postID, commentId, comment_desc, commentDate, active) values (?, ?, null, ?, NOW(), 1)");
            ps.setInt(1, userId);
            ps.setInt(2, postId);
            ps.setString(3, desc);
            ps.executeUpdate();
            System.out.println("Comment has been added.");
            flag = true;

        } catch (SQLException e) {
            System.out.println("Exception occured in the makeCommment() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the makeCommment() method: " + e.getMessage());
            }
        }
        return flag;
    }

    @Override
    public boolean deleteComment(int commentID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE comments SET status = 0, comment_desc = '[deleted]' WHERE commentID = ?");
            ps.setInt(1, commentID);
            ps.executeUpdate();
            System.out.println("Comment has been deleted.");
            flag = true;
        } catch (SQLException e) {
            System.out.println("Exception occured in the deleteComment() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the deleteComment() method: " + e.getMessage());
            }
        }
        return flag;
    }

    @Override
    public boolean updateComment(int commentID, String desc) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE comments SET comment_desc = ? WHERE commentId = ?");
            ps.setString(1, desc);
            ps.setInt(2, commentID);
            ps.executeUpdate();
            System.out.println("Comment has been updated.");
            flag = true;
        } catch (SQLException e) {
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
    public int countCommentOfPost(int postId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //List<Comment> comments = new ArrayList();

        try {

            conn = getConnection();
            String query = "SELECT COUNT(c.commentId) FROM comments AS c INNER JOIN posts AS p on p.postId =c.postId WHERE c.postId =? And c.status =1";
            ps = conn.prepareStatement(query);
            ps.setInt(1, postId);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException se) {
            System.out.println("SQL Exception occurred: " + se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
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
        return count;

    }

    public static void main(String[] args) {
        ICommentDao c = new CommentDao("repos");
        System.out.println(c.countCommentOfPost(22));
    }

}
