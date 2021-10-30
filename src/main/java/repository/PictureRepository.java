/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import configuration.MySqlConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PictureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */
@Repository
public class PictureRepository {
    @Autowired
    private PasswordEncoder pe;
    @Autowired
    private MySqlConnector sqlDB;
//    public String addPicture(PictureModel pic){
//        String sql ="INSERT INTO tblPicture (`url`,`size`,`tag`,`description`,`title`,`filetype`) VALUES(?,?,?,?,?,?)";
//        try {
//            PreparedStatement ps = sqlDB.con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, pic.getUrl());
//            ps.setString(2, pic.getSize());
//            ps.setString(3, pic.getTag());
//            ps.setString(4, pic.getDescription());
//            ps.setString(5, pic.getTitle() );
//            ps.setString(6, pic.getFiletype());
//            ps.executeUpdate(); 
//            ResultSet rs = ps.getGeneratedKeys();  
//            int id = rs.next() ? rs.getInt(1) : 0;
//            pic.setId(id);
//            return "SUCCESS";
//        } catch (SQLException ex) {
//            Logger.getLogger(PictureRepository.class.getName()).log(Level.SEVERE, null, ex);
//            return ex.getMessage();
//        }
//        
//    }
}
