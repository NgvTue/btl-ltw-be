/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuration;

import configuration.DatabaseConnector;
import java.sql.DriverManager;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuenguyen
 */


public class MySqlConnector extends DatabaseConnector {
    @Override
    public void connect() {
        System.out.println(this.getUrl());
        String dbUrl = this.getUrl();
        String dbClass = "com.mysql.jdbc.Driver";
        if(con != null){
            return;
        }
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection (dbUrl, this.getUsername(),this.getPassword());
            System.out.println("Đã kết nối tới Mysql: " + getUrl());
        }catch(Exception e) {
            System.out.println("loi o day");
            e.printStackTrace();
            
        }
        
    }
    
    
}
