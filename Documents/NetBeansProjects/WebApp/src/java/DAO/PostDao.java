/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Post;
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
public class PostDao extends Dao implements IPostDao {

    public PostDao(String database) {
        super(database);
    }

    @Override
    public List<Post> getAllPosts() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Post> posts = new ArrayList();

        try {
            con = getConnection();
            String query = "SELECT u.username, c.communityName, p.postID, p.userID, p.postTitle, p.postDesc, p.postDate, p.media, p.status FROM posts p INNER JOIN users u ON u.userId = 35 LEFT JOIN community c ON c.communityId = p.communityId WHERE p.STATUS = 1 ORDER BY p.postDate DESC";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
              Post p = new Post(
                        rs.getInt("postId"),
                        rs.getInt("userId"),
                        rs.getString("postTitle"),
                        rs.getString("postDesc"),
                        rs.getString("postDate"),
                        rs.getString("media"),
                        rs.getInt("status"),
                        rs.getString("username"),
                        rs.getString("communityName")
                );
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllPosts() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllPosts() method: " + e.getMessage());
            }
        }

        return posts;
    }

    @Override
    public List<Post> getOnePost(int postId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Post> post = new ArrayList();

        try {
            con = getConnection();

            String query = "SELECT u.username, c.communityName, p.postID, p.userID, p.postTitle, p.postDesc, p.postDate, p.media, p.status FROM posts p INNER JOIN users u ON u.userId = p.userId LEFT JOIN community c ON c.communityId = p.communityId WHERE p.STATUS = 1 AND p.postID=? ORDER BY p.postDate DESC";
            ps = con.prepareStatement(query);
            ps.setInt(1, postId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("postId"),
                        rs.getInt("userId"),
                        rs.getString("postTitle"),
                        rs.getString("postDesc"),
                        rs.getString("postDate"),
                        rs.getString("media"),
                        rs.getInt("status"),
                        rs.getString("username"),
                        rs.getString("communityName")
                );
                
                post.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllPosts() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllPosts() method: " + e.getMessage());
            }
        }

        return post;
    }

    @Override
    public List<Post> getPostsByUser(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Post> posts = new ArrayList();

        try {
            con = getConnection();
            String query = "SELECT u.username, c.communityName, p.postID, p.userID, p.postTitle, p.postDesc, p.postDate, p.media, p.status FROM posts p INNER JOIN users u ON u.userId = p.userId LEFT JOIN community c ON c.communityId = p.communityId WHERE p.STATUS = 1 AND p.userId = ? ORDER BY p.postDate DESC";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
             Post p = new Post(
                        rs.getInt("postId"),
                        rs.getInt("userId"),
                        rs.getString("postTitle"),
                        rs.getString("postDesc"),
                        rs.getString("postDate"),
                        rs.getString("media"),
                        rs.getInt("status"),
                        rs.getString("username"),
                        rs.getString("communityName")
                );
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Exception occured in the getAllPosts() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the getAllPosts() method: " + e.getMessage());
            }
        }

        return posts;
    }

    @Override
    public boolean makePost(int userId, String postTitle, String postDesc, String media) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;

        try {
            con = getConnection();

            ps = con.prepareStatement("insert into posts (postID, userId, postTitle, postDesc, postDate, media, status) values (null, ?, ?, ?, NOW(), ?, 1)");
            ps.setInt(1, userId);
            ps.setString(2, postTitle);
            ps.setString(3, postDesc);
            ps.setString(4, media);
            ps.executeUpdate();
            System.out.println("Post has been added.");
            flag = true;

        } catch (SQLException e) {
            System.out.println("Exception occured in the makePost() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the makePost() method: " + e.getMessage());
            }
        }
        return flag;
    }

    @Override
    public boolean deletePost(int postId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE posts SET status = 0, postTitle = '[deleted]', postDesc = '[deleted]' WHERE postId = ?");
            ps.setInt(1, postId);
            ps.executeUpdate();
            System.out.println("Post has been deleted.");
            flag = true;
        } catch (SQLException e) {
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

    @Override
    public boolean updatePost(int postId, String title, String desc) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE posts SET postTitle = ?,postDesc =? WHERE postId = ?");
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setInt(3, postId);
            ps.executeUpdate();
            System.out.println("Post has been updated.");
            flag = true;
        } catch (SQLException e) {
            System.out.println("Exception occured in the updateAPost() method: " + e.getMessage());
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
                System.out.println("Exception occured in the finally section of the updateAPost() method: " + e.getMessage());
            }
        }
        return flag;

        ///if user exist in the database
    }

    public static void main(String[] args) {
        PostDao pd = new PostDao("repos");
        // System.out.println(pd.makePost(35, "hola mundo", "com el teu grupo no ho fan seus feina te quedaras falta i sense res per fer", "ni una mierda"));
        //System.out.println(pd.getPostsByUser(36));
        System.out.println(pd.getOnePost(23));
    }
}
