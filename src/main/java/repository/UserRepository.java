/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import configuration.MySqlConnector;
import static java.lang.String.format;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import model.UserModel;

/**
 *
 * @author tuenguyen
 */

@Repository
public class UserRepository {
    @Autowired
    private PasswordEncoder pe;
    @Autowired
    private MySqlConnector sqlDB;
    public UserModel getUser(String name, String password){
        String sql = "SELECT * FROM tblUser WHERE userName=? AND password=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                
                int id = rs.getInt("userID");
                UserModel user = new UserModel(name, password);
                return user;
//                return true;
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        return null;
    }
    public  Optional<UserModel>  findByUsername(String username){
        
        String sql = "SELECT * FROM tblUser WHERE username=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setString(1, username);
            System.out.println("here sql start");
         
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                System.out.println("here sql"); 
                int id = rs.getInt("id");
                System.out.println(id);
                String password  = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String dob=rs.getString("dob");
                String phone=rs.getString("phone");
                String fullname=rs.getString("fullname");
                String add = rs.getString("address");
                String create = rs.getString("createdate");
                String urlProfile = rs.getString("urlProfilePicture");
                String urlBg = rs.getString("urlBackgroundPicture");
                System.out.println("i");
                UserModel us =new UserModel();
                us.setId(id);
                us.setUsername(username);
                us.setPassword(password);
                us.setAddress(add);
                us.setCreatedAt(create);
                us.setEmail(email);
                us.setDob(dob);
                us.setPhone(phone);
                us.setRole(role);
                us.setFullname(fullname);
                us.setUrlBackgroundPicture(urlBg);
                us.setUrlProfilePicture(urlProfile);
                System.out.println("herree");
                
                return Optional.ofNullable(us);
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        return null;
    }
    
    public String setUser(UserModel user) {
        String sql = "UPDATE tblUser "
                + " SET password=? , email=?, dob=?, phone=?, role=?,fullname=?,address= ?"
                + "  WHERE username=?";
        PreparedStatement ps;
        try {
            ps = sqlDB.con.prepareStatement(sql);
            //        ps.setString(1, user.getUsername());
            System.out.println(user.getEmail());
            ps.setString(1,user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setString(3,user.getDob());
            ps.setString(4, user.getPhone());
            ps.setString(5,user.getRole());
            ps.setString(6, user.getFullname());

            ps.setString(7, user.getAddress());
            ps.setString(8, user.getUsername());
            System.out.println(ps.toString());
    //        ps.setString(9,user.getCreatedAt());
    //            ResultSet rs = ps.executeQuery();
            ps.executeUpdate(); 
        } catch (SQLException ex) {
            return "FAILED|" + ex.getMessage();
        }

        return "SUCCESS";
    }
    public  Optional<UserModel>  findByUsernamePassword(String username,String password){
        
        String sql = "SELECT * FROM tblUser WHERE username=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setString(1, username);
            System.out.println("here sql");
         
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                System.out.println("here"); 
                int id = rs.getInt("id");
                String passwordencode  = rs.getString("password");
                System.out.println(password);
                if(!password.equals(passwordencode)){
                    System.out.println("sai passw");
                    System.out.println(pe.encode( password));
                    System.out.println( passwordencode);
                    return null;
                }
                String email = rs.getString("email");
                String role = rs.getString("role");
                int profileid= -1;
                String urlProfile = rs.getString("urlProfilePicture");
                String urlBg = rs.getString("urlBackgroundPicture");
                UserModel us = new UserModel(
                        username,
                        password,
                        email,
                        role,
                        id,
                        profileid
                );
                us.setUrlProfilePicture(urlProfile);
                us.setUrlBackgroundPicture(urlBg);
                return Optional.ofNullable(us);
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        return null;
    }
    
    public  Optional<UserModel>  findById(int id){
        
        String sql = "SELECT * FROM tblUser WHERE id=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println("here sql");
         
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                System.out.println("here"); 
//                int id = rs.getInt("id");
                String username =rs.getString("username");
                String fullname = rs.getString("fullname");
                String address = rs.getString("address");
                String dob = rs.getString("dob");
                String phone = rs.getString("phone");
                
                
                String email = rs.getString("email");
                String role = rs.getString("role");
                int profileid= -1;
                String urlProfile = rs.getString("urlProfilePicture");
                String urlBg = rs.getString("urlBackgroundPicture");
                UserModel us = new UserModel(
                        username,
                        null,
                        email,
                        role,
                        id,
                        profileid
                );
                us.setAddress(address);
//                us.setCreatedAt(role);
                us.setFullname(fullname);
                us.setPhone(phone);
                us.setDob(dob);
                us.setUrlProfilePicture(urlProfile);
                us.setUrlBackgroundPicture(urlBg);
                return Optional.ofNullable(us);
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        return null;
    }
    
    public boolean  CheckUserName(String name){
        String sql = "SELECT * FROM tblUser WHERE username=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setString(1, name);
           
            
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                
              return true;
//                return true;
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        return false;
    }

    public String RegistUser(UserModel user) {
        if (CheckUserName(user.getUsername())){
            return "FAILED user exits";
        }
        user.setPassword(pe.encode(user.getPassword()));
        int profileid=-1;
//        String sql = "INSERT INTO `ltw`.`tblUser` (`username`, `email`, `password`, `role`, `id`, `profileid`) VALUES (?, ?,?, ?, ?, ?);";
        String sql="INSERT INTO `tblUser` ( `username`, `password`, `email`, `dob`, `phone`, `role`, `fullname`, `address`, `createdate`,`urlProfilePicture`,`urlBackgroundPicture`) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?,?,?);";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4,user.getDob());
            ps.setString(5, user.getPhone());
            ps.setString(6,user.getRole());
            ps.setString(7, user.getFullname());
            
            ps.setString(8, user.getAddress());
            
            ps.setString(9,user.getCreatedAt());
            ps.setString(10, user.getUrlProfilePicture());
            ps.setString(11, user.getUrlBackgroundPicture());
//            ResultSet rs = ps.executeQuery();
            ps.executeUpdate(); 
            ResultSet rs = ps.getGeneratedKeys();  
            int id = rs.next() ? rs.getInt(1) : 0;
            
            user.setId(id);
            
            
            
            return "SUCESS";
    
                      //DO NOT close the connection here!
            }
        catch(Exception e){
               return e.getMessage();
        }  
        
        
        
    }
    public boolean isFollow(
            UserModel userFrom,
            UserModel userTo
    ) throws SQLException{
        String sql="SELECT * FROM tblFollow WHERE `from`=? AND `to`=?";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setInt(1, userFrom.getId());
        ps.setInt(2, userTo.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else
        {
            return false;
        }
        
    }
    public String followAction(UserModel userCurrent, UserModel userWantFollow, String curentTime) throws SQLException {
        
        if(isFollow(userCurrent, userWantFollow)){
            System.out.println("failed follow");
            return "FAILED| user already follow";
        }
        else
        {
            String sql  ="INSERT INTO tblFollow (`from`,`to`,`timeFollow`) VALUES(?,?,?)";
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setInt(1, userCurrent.getId());
            ps.setInt(2, userWantFollow.getId());
            ps.setString(3, curentTime);//, sqlxml);
            ps.executeUpdate();
            
            return "SUCCESS";
        }
        
    }

    
   
    
    
}
