/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import configuration.MySqlConnector;
import static java.lang.String.format;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
                String password  = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String dob=rs.getString("dob");
                String phone=rs.getString("phone");
                String fullname=rs.getString("fullname");
                String add = rs.getString("address");
                String create = rs.getString("createdate");
                
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
                
                
                return Optional.ofNullable(us);
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        return null;
    }
    
     public  Optional<UserModel>  findByUsernamePassword(String username,String password){
        
        String sql = "SELECT * FROM tblUser WHERE username=?";
        try{
            PreparedStatement ps = sqlDB.con.prepareStatement(sql);
            ps.setString(1, username);
            System.out.println("here");
         
            ResultSet rs = ps.executeQuery();
 
            if(rs.next()){
                System.out.println("here"); 
                int id = rs.getInt("id");
                String passwordencode  = rs.getString("password");
                if(!pe.encode(password).equals( passwordencode)){
                    return null;
                }
                String email = rs.getString("email");
                String role = rs.getString("role");
                int profileid= rs.getInt("profileid");
                UserModel us = new UserModel(
                        username,
                        password,
                        email,
                        role,
                        id,
                        profileid
                );
                
                return Optional.ofNullable(us);
            }
                  //DO NOT close the connection here!
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        return null;
    }
    public boolean  CheckUserName(String name){
        String sql = "SELECT * FROM tblUser WHERE userName=?";
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

    String RegistUser(UserModel user) {
        if (CheckUserName(user.getUsername())){
            return "FAILED user exits";
        }
        user.setPassword(pe.encode(user.getPassword()));
        int profileid=-1;
//        String sql = "INSERT INTO `ltw`.`tblUser` (`username`, `email`, `password`, `role`, `id`, `profileid`) VALUES (?, ?,?, ?, ?, ?);";
        String sql="INSERT INTO `test`.`tblUser` ( `username`, `password`, `email`, `dob`, `phone`, `role`, `fullname`, `address`, `createdate`) VALUES (?, ?, ?, ?, ?, ?,?, ?, ?);";
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

    
   
    
    
}
