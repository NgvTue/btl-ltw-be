/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import configuration.MySqlConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.NotiModel;
import model.PostModel;
import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */
@Repository
public class NotiRepository {
    @Autowired
    private MySqlConnector sqlDB;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private PostRepository postRepo;
    
    public NotiModel createNoti(NotiModel noti) throws SQLException{
        //
        int idUserCreatedNoti = noti.getFrom().getId();
        int idUserRecevieNoti = noti.getTo().getId();
        int idPost =noti.getPost().getIdPost();
        String description = noti.getDescription();
        String type  = noti.getType();
        String urlLink = noti.getUrlNotification();
        
        
        String sql = "INSERT INTO tblNoti(`type`,`from`,`to`,`description`,`urlLink`,`idPost`) VALUES(?,?,?,?,?,?)";
        
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setString(1, type);
        ps.setInt(2, idUserCreatedNoti);
        ps.setInt(3, idUserRecevieNoti);
        ps.setString(4, description);
        ps.setString(5, urlLink);
        ps.setInt(6, idPost);
        
        ps.executeUpdate();
        return noti;
        
    }
    public ArrayList<NotiModel> getAllNotiOfUser(
            UserModel user
    ) throws SQLException{
        
        int idUser = user.getId();
        String sql = "SELECT * FROM tblNoti  WHERE `to`=? AND `isView`=?";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setInt(1, idUser);
        ps.setInt(2, 0);
        ResultSet rs = ps.executeQuery();
        
        
        ArrayList<NotiModel> notis = new ArrayList<NotiModel> (0);
        while(rs.next()){
            // 
            int idNoti = rs.getInt("id");
            String type = rs.getString("type");
            int from = rs.getInt("from");
            int to=rs.getInt("to");
            int idPost = rs.getInt("idPost");
            String description = rs.getString("description");
            String urlLink = rs.getString("urlLink");
            NotiModel noti = new NotiModel();
            noti.setId(idNoti);
            noti.setType(type);
            noti.setDescription(description);
            
            // get User;
            noti.setFrom(userRepo.findById(from).orElse(null));
            noti.setTo(userRepo.findById(to).orElse(null));
            
            PostModel post = new PostModel();
            post.setIdPost(idPost);
            noti.setPost(postRepo.getDetailPost(post));
            notis.add(noti);
            
        }
        
        String sqlSet  = "UPDATE  tblNoti SET isView=? WHERE `to` = ?";
        PreparedStatement psSet = sqlDB.con.prepareStatement(sqlSet);
        psSet.setInt(1, 1);
        psSet.setInt(2, idUser);
        psSet.executeUpdate();
        
        return notis;
        
    }
}
