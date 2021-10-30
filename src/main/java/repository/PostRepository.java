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
    
    public String createPost(PostModel post) throws SQLException{
        String sql = "SELECT * FROM tblUser  WHERE userName = ? ";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setString(1, post.getUserCreate());


        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int userId = rs.getInt("id");
            String sqlInsertPost = "INSERT INTO tblPost (`title`,`description`,`loveCount`,`pictureDescription`,`urlDesign`,`userCreate`) "
                    + "VALUES(?,?,?,?,?,?)";
            PreparedStatement ps1 = sqlDB.con.prepareStatement(sqlInsertPost,Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, post.getTitlePost());
            ps1.setString(2, post.getDescriptionPost());
            ps1.setInt(3, post.getLoveCount());
            ps1.setString(4, post.getDescriptionPicture());
            ps1.setString(5, post.getUrlDesign());
            ps1.setInt(6, userId );
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
