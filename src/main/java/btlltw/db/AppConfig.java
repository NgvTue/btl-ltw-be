/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btlltw.db;

/**
 *
 * @author tuenguyen
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Lấy giá trị config từ file application.properties
    @Value("${tuenguyen.mysql.url}")
    String mysqlUrl;
    @Value("${tuenguyen.mysql.username}")
    String username;
    @Value("${tuenguyen.mysql.password}")
    String password;
    
    @Bean("mysqlConnector")
    MySqlConnector mysqlConfigure() {
        MySqlConnector mySqlConnector = new MySqlConnector();
        // Set Url
        System.out.println("Config Mysql Url: " + mysqlUrl);
        mySqlConnector.setUrl(mysqlUrl);
        mySqlConnector.setPassword(password);
        mySqlConnector.setUsername(username);
        mySqlConnector.connect();
        // Set username, password, format, v.v...
        return mySqlConnector;
    }
}