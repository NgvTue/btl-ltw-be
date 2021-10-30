/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import configuration.MySqlConnector;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */
@Repository
public class PostRepository {
    @Autowired
    private MySqlConnector sqlDB;
    public PostModel getDetailPost(PostModel post) throws SQLException{
        String sql ="SELECT * FROM tblPost where id = ?";
        PreparedStatement ps =sqlDB.con.prepareStatement(sql);
        ps.setInt(1, post.getIdPost());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            PostModel p  = new PostModel();
            int idPost = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            int countLove =rs.getInt(4);
            String pictureDescription = rs.getString(5);
            String urlDesign = rs.getString(6);
            int idUser = rs.getInt(7);
            int price = rs.getInt(8);
            String username="";
            String sqlUser = "SELECT userName FROM tblUser WHERE id=?";
            PreparedStatement psSqlUser = sqlDB.con.prepareStatement(sqlUser);
            psSqlUser.setInt(1, idUser);
            ResultSet rs1 = psSqlUser.executeQuery();
            if(rs1.next()){
                username = rs1.getString("userName");
            }
            
            // get tags
            
            String sqlTags = "SELECT * FROM tblTags WHERE idPost = ?";
            PreparedStatement psSqlTags = sqlDB.con.prepareStatement(sqlTags);
            psSqlTags.setInt(1, idPost);
            ResultSet rs2 = psSqlTags.executeQuery();
            
            ArrayList<String> tags = new ArrayList<>(0);
            
            while(rs2.next()){
//                System.out.println(rs2.getString(1));
                tags.add(rs2.getString("name"));
            }
            p.setIdPost(idPost);
            p.setUrlPicture(pictureDescription);
            p.setDescriptionPost(description);
            p.setLoveCount(countLove);
            p.setPrice(price);
            p.setTags(tags);
            p.setUrlDesign(urlDesign);
            p.setUserCreate(username);
            p.setTitlePost(title);
            
            return p;
        }
        return null;
        
    }
    public ArrayList<PostModel> getAllPosts() throws SQLException{
        String sql ="SELECT * FROM tblPost";
        PreparedStatement ps =sqlDB.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<PostModel> posts = new ArrayList<>(0);
        while(rs.next()){
            PostModel p  = new PostModel();
            int idPost = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            int countLove =rs.getInt(4);
            String pictureDescription = rs.getString(5);
            String urlDesign = rs.getString(6);
            int idUser = rs.getInt(7);
            int price = rs.getInt(8);
            String username="";
            String sqlUser = "SELECT userName FROM tblUser WHERE id=?";
            PreparedStatement psSqlUser = sqlDB.con.prepareStatement(sqlUser);
            psSqlUser.setInt(1, idUser);
            ResultSet rs1 = psSqlUser.executeQuery();
            if(rs1.next()){
                username = rs1.getString("userName");
            }
            
            // get tags
            
            String sqlTags = "SELECT * FROM tblTags WHERE idPost = ?";
            PreparedStatement psSqlTags = sqlDB.con.prepareStatement(sqlTags);
            psSqlTags.setInt(1, idPost);
            ResultSet rs2 = psSqlTags.executeQuery();
            
            ArrayList<String> tags = new ArrayList<>(0);
            
            while(rs2.next()){
//                System.out.println(rs2.getString(1));
                tags.add(rs2.getString("name"));
            }
            p.setIdPost(idPost);
            p.setUrlPicture(pictureDescription);
            p.setDescriptionPost(description);
            p.setLoveCount(countLove);
            p.setPrice(price);
            p.setTags(tags);
            p.setUrlDesign(urlDesign);
            p.setUserCreate(username);
            p.setTitlePost(title);
            posts.add(p);
            
            
            
        }
        return posts;
    }
    public String createPost(PostModel post) throws SQLException{
        String sql = "SELECT * FROM tblUser  WHERE userName = ? ";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setString(1, post.getUserCreate());


        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int userId = rs.getInt("id");
            String sqlInsertPost = "INSERT INTO tblPost (`title`,`description`,`loveCount`,`urlPicture`,`urlDesign`,`userCreate`,`price`) "
                    + "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps1 = sqlDB.con.prepareStatement(sqlInsertPost,Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, post.getTitlePost());
            ps1.setString(2, post.getDescriptionPost());
            ps1.setInt(3, post.getLoveCount());
            ps1.setString(4, post.getUrlPicture());
            ps1.setString(5, post.getUrlDesign());
            ps1.setInt(6, userId );
            ps1.setInt(7, post.getPrice());
            ps1.executeUpdate(); 
            
            ResultSet rs1 = ps1.getGeneratedKeys();  
            int idPost = rs1.next() ? rs1.getInt(1) : 0;
            
            ArrayList<String> tags = post.getTags();
            
            for (String tag : tags){
                String sqlInsertTags = "INSERT INTO tblTags (`idPost`,`name`) VALUES(?,?)";
                PreparedStatement ps2 = sqlDB.con.prepareStatement(sqlInsertTags);
                ps2.setInt(1, idPost);
                ps2.setString(2, tag);
                ps2.execute();
            }
            return "SUCCESS";
        }
        else
        {
            return "FAILED | userName is not avaliable";
            // failed here
        }
        
    }
}
