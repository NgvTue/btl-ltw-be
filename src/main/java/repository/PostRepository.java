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
import model.UserModel;
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
            
            String sqlSelectLike = "SELECT COUNT(*) as total FROM tblPostLike WHERE idPost=?";
            PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
            psSqlSlectLike.setInt(1, idPost);
            ResultSet reLike = psSqlSlectLike.executeQuery();
            int countLike = 0;
            if(reLike.next()){
                countLike = reLike.getInt("total");
            }
            
            
            String pictureDescription = rs.getString(4);
            String urlDesign = rs.getString(5);
            int idUser = rs.getInt(6);
            int price = rs.getInt(7);
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
            p.setLoveCount(countLike);
            p.setPrice(price);
            p.setTags(tags);
            p.setUrlDesign(urlDesign);
            p.setUserCreate(username);
            p.setTitlePost(title);
            p.setIdUserCreated(idUser);
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
            
            int countLike =0;
            String sqlSelectLike = "SELECT COUNT(*) as total FROM tblPostLike WHERE idPost=?";
            PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
            psSqlSlectLike.setInt(1, idPost);
            ResultSet reLike = psSqlSlectLike.executeQuery();
            if(reLike.next()){
                countLike = reLike.getInt("total");
            }
            
            
            String pictureDescription = rs.getString(4);
            String urlDesign = rs.getString(5);
            int idUser = rs.getInt(6);
            int price = rs.getInt(7);
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
            p.setLoveCount(countLike);
            p.setPrice(price);
            p.setTags(tags);
            p.setUrlDesign(urlDesign);
            p.setUserCreate(username);
            p.setTitlePost(title);
            p.setIdUserCreated(idUser);
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
            String sqlInsertPost = "INSERT INTO tblPost (`title`,`description`,`urlPicture`,`urlDesign`,`userCreate`,`price`) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps1 = sqlDB.con.prepareStatement(sqlInsertPost,Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, post.getTitlePost());
            ps1.setString(2, post.getDescriptionPost());
           
            ps1.setString(3, post.getUrlPicture());
            ps1.setString(4, post.getUrlDesign());
            ps1.setInt(5, userId );
            ps1.setInt(6, post.getPrice());
            ps1.executeUpdate(); 
            
            ResultSet rs1 = ps1.getGeneratedKeys();  
            int idPost = rs1.next() ? rs1.getInt(1) : 0;
            
            String sqlTags = "SELECT * FROM tblTags";
            PreparedStatement psSqlTags = sqlDB.con.prepareStatement(sqlTags);
            ResultSet rs2 = psSqlTags.executeQuery();

            ArrayList<String> tagCurrents = new ArrayList<>(0);

            while(rs2.next()){
                tagCurrents.add(rs2.getString("name"));
            }
            
            ArrayList<String> tags = post.getTags();
            
            for (String tag : tags){
                if (tagCurrents.indexOf(tag) == -1) {
                    String sqlInsertTags = "INSERT INTO tblTags (`idPost`,`name`) VALUES(?,?)";
                    PreparedStatement ps2 = sqlDB.con.prepareStatement(sqlInsertTags);
                    ps2.setInt(1, idPost);
                    ps2.setString(2, tag);
                    ps2.execute();
                }
            }
            return "SUCCESS";
        }
        else
        {
            return "FAILED | userName is not avaliable";
            // failed here
        }
        
    }
    
    String updatePost(PostModel post) throws SQLException{
        
        String sql ="SELECT * FROM tblPost where id = ?";
        PreparedStatement ps =sqlDB.con.prepareStatement(sql);
        ps.setInt(1, post.getIdPost());
        ResultSet rs = ps.executeQuery();
        if(!rs.next()){
            return "FAILED|post doesn't exit";
        }
        
        String sqlUpdate = "UPDATE tblPost SET (`title`=?,`description`=?,`urlPicture`=?,`urlDesign`=?,`price`=?)"
                + "WHERE id = ?";
        PreparedStatement psUpdate = sqlDB.con.prepareStatement(sqlUpdate);
        psUpdate.setString(1, post.getTitlePost());
        psUpdate.setString(2, post.getDescriptionPost());
        psUpdate.setString(3, post.getUrlPicture());
        psUpdate.setString(4, post.getUrlDesign());
        psUpdate.setInt(5, post.getPrice());
        ResultSet rs2 = psUpdate.executeQuery();
        return "SUCCES";
    }
    public String updateLikePost(PostModel post, UserModel userLiked, String type) throws SQLException{
        // 
        if(type.equalsIgnoreCase("like")){
            String sqlCheck = "SELECT * FROM tblPostLike WHERE idPost = ? AND idUser = ?";
            PreparedStatement psCheck = sqlDB.con.prepareStatement(sqlCheck);
            psCheck.setInt(2, userLiked.getId());
            psCheck.setInt(1, post.getIdPost());
            
            ResultSet rs = psCheck.executeQuery();
            if(rs.next()){
                // user da like 
                return "FAILED|user liked before";
            }
            else
            {
                // add tlbPost;ike
                
                String sqlInsert = "INSERT INTO tblPostLike(`idPost`,`idUser`) VALUES(?,?)";
                PreparedStatement psInsert = sqlDB.con.prepareStatement(sqlInsert);
                psInsert.setInt(1, post.getIdPost());
                psInsert.setInt(2, userLiked.getId());
                psInsert.executeUpdate();
                return "SUCCESS|OK";
                
            }
            
        }
        else
        {
            String sqlCheck = "SELECT * FROM tblPostLike WHERE idPost = ? AND idUser = ?";
            PreparedStatement psCheck = sqlDB.con.prepareStatement(sqlCheck);
            psCheck.setInt(2, userLiked.getId());
            psCheck.setInt(1, post.getIdPost());
            
            ResultSet rs = psCheck.executeQuery();
            if(!rs.next()){
                // chua co trong db
                return "FAILED|user didn't like this post before";
            }
            else
            {
                String sqlInsert = "DELETE FROM where idPost = ? AND idUser=?";
                PreparedStatement psInsert = sqlDB.con.prepareStatement(sqlInsert);
                psInsert.setInt(1, post.getIdPost());
                psInsert.setInt(2, userLiked.getId());
                ResultSet reInsert = psInsert.executeQuery();
                return "SUCCESS|OK";
            }
        }
        
    }
}
