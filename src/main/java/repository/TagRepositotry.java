/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import configuration.MySqlConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tvthag
 */
@Repository
public class TagRepositotry {
    @Autowired
    private MySqlConnector sqlDB;
    public ArrayList<String> getAllTags() throws SQLException{
        String sqlTags = "SELECT * FROM tblTags";
        PreparedStatement psSqlTags = sqlDB.con.prepareStatement(sqlTags);
        ResultSet rs2 = psSqlTags.executeQuery();

        ArrayList<String> tags = new ArrayList<>(0);

        while(rs2.next()){
            String tg = rs2.getString("name");
            if (tags.indexOf(tg) == -1) {
                tags.add(tg);
            }
        }
            
        return tags;
    }
}
