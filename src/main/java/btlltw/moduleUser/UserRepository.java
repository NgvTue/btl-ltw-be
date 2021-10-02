/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btlltw.moduleUser;

import btlltw.db.MySqlConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuenguyen
 */

@Repository
public class UserRepository {
    
    @Autowired
    private MySqlConnector sqlDB;
    
    public UserModel getUser(String name, String password){
//        System.out.println(sqlDB.getPassword());
//        sqlDB.setPassword(password);
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
    public String RegistUser(String name, String password){
        if (CheckUserName(name)){
            return "FAILED-UserName already in DB";
        }
        else
        {
            String sql = "INSERT INTO tblUser (userName,password) VALUES(?,?)";
            try{
                PreparedStatement ps = sqlDB.con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, password);
                boolean rs = ps.execute();
                return "SUCESS";
    
                      //DO NOT close the connection here!
            }catch(Exception e){
                e.printStackTrace();
            }   
            
        }
        return "????";
    }
    
}
