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
import model.Comment;
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
    @Autowired
    private UserRepository userRepo;
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
            
            String sqlSelectLike = "SELECT * FROM tblPostLike WHERE idPost=?";
            PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
            psSqlSlectLike.setInt(1, idPost);
            ResultSet reLike = psSqlSlectLike.executeQuery();
            int countLike = 0;
            ArrayList<Integer> userLike = new ArrayList<>(0);
            while(reLike.next()){
                countLike += 1;
                userLike.add(
                    Integer.valueOf(reLike.getInt("idUser"))
                );
                
            }
            
            
            String pictureDescription = rs.getString(4);
            String urlDesign = rs.getString(5);
            int idUser = rs.getInt(6);
            int price = rs.getInt(7);
            String username="";
            
            UserModel u = userRepo.findById(idUser).orElse(null);
            
           
            username = u.getUsername();
                
            
            
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
            p.setNotiFromUsers(userLike);
            p.setUserCreateModel(u);
            return p;
        }
        return null;
        
    }
    
    public ArrayList<PostModel> getPostsLiked(int userID) throws SQLException{
        ArrayList<PostModel> arrLikedPosts = new ArrayList<>();
        String sqlLiked = "SELECT idPost FROM tblPostLike WHERE idUser=?";
        PreparedStatement psSqlLiked = sqlDB.con.prepareStatement(sqlLiked);
        psSqlLiked.setInt(1, userID);
        ResultSet liked = psSqlLiked.executeQuery();
        while(liked.next()){
            String sql ="SELECT * FROM tblPost where id = ?";
            PreparedStatement ps =sqlDB.con.prepareStatement(sql);
            ps.setInt(1, liked.getInt("idPost"));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PostModel p  = new PostModel();
                int idPost = rs.getInt(1);
                String title = rs.getString(2);
                String description = rs.getString(3);

                String sqlSelectLike = "SELECT * FROM tblPostLike WHERE idPost=?";
                PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
                psSqlSlectLike.setInt(1, idPost);
                ResultSet reLike = psSqlSlectLike.executeQuery();
                int countLike = 0;
                ArrayList<Integer> userLike = new ArrayList<>(0);
                while(reLike.next()){
                    countLike += 1;
                    userLike.add(
                        Integer.valueOf(reLike.getInt("idUser"))
                    );

                }


                String pictureDescription = rs.getString(4);
                String urlDesign = rs.getString(5);
                int idUser = rs.getInt(6);
                int price = rs.getInt(7);
                String username="";

                UserModel u = userRepo.findById(idUser).orElse(null);


                username = u.getUsername();



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
                p.setNotiFromUsers(userLike);
                p.setUserCreateModel(u);
                arrLikedPosts.add(p);
            }
        }
        
        return arrLikedPosts;
        
    }
    
    public ArrayList<PostModel> getPostsByTags(ArrayList<String> tagPosts) throws SQLException{
        ArrayList<PostModel> arrTagPosts = new ArrayList<>();
        for (String tagPost: tagPosts) {
            String sqlLiked = "SELECT idPost FROM tblTags WHERE name=?";
            PreparedStatement psSqlLiked = sqlDB.con.prepareStatement(sqlLiked);
            psSqlLiked.setString(1, tagPost);
            ResultSet liked = psSqlLiked.executeQuery();
            while(liked.next()){
                String sql ="SELECT * FROM tblPost where id = ?";
                PreparedStatement ps =sqlDB.con.prepareStatement(sql);
                ps.setInt(1, liked.getInt("idPost"));
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    PostModel p  = new PostModel();
                    int idPost = rs.getInt(1);
                    String title = rs.getString(2);
                    String description = rs.getString(3);

                    String sqlSelectLike = "SELECT * FROM tblPostLike WHERE idPost=?";
                    PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
                    psSqlSlectLike.setInt(1, idPost);
                    ResultSet reLike = psSqlSlectLike.executeQuery();
                    int countLike = 0;
                    ArrayList<Integer> userLike = new ArrayList<>(0);
                    while(reLike.next()){
                        countLike += 1;
                        userLike.add(
                            Integer.valueOf(reLike.getInt("idUser"))
                        );

                    }


                    String pictureDescription = rs.getString(4);
                    String urlDesign = rs.getString(5);
                    int idUser = rs.getInt(6);
                    int price = rs.getInt(7);
                    String username="";

                    UserModel u = userRepo.findById(idUser).orElse(null);


                    username = u.getUsername();



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
                    p.setNotiFromUsers(userLike);
                    p.setUserCreateModel(u);
                    arrTagPosts.add(p);
                }
            }
        }
        
        
        return arrTagPosts;
        
    }
    
    public ArrayList<PostModel> getAllPosts() throws SQLException{
        String sql ="SELECT * FROM tblPost";
        PreparedStatement ps =sqlDB.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<PostModel> posts = new ArrayList<>(0);
        while(rs.next()){
            PostModel p  = new PostModel();
            int idPost = rs.getInt(1);
            if(idPost==1){
                continue;
            }
            String title = rs.getString(2);
            String description = rs.getString(3);
            
            int countLike =0;
            String sqlSelectLike = "SELECT * FROM tblPostLike WHERE idPost=?";
            PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
            psSqlSlectLike.setInt(1, idPost);
            ResultSet reLike = psSqlSlectLike.executeQuery();
            
            ArrayList<Integer> userLike = new ArrayList<>(0);
            while(reLike.next()){
                countLike += 1;
                userLike.add(
                    Integer.valueOf(reLike.getInt("idUser"))
                );
                
            }
            
            
            String pictureDescription = rs.getString(4);
            String urlDesign = rs.getString(5);
            int idUser = rs.getInt(6);
            int price = rs.getInt(7);
            String username="";
            UserModel u = userRepo.findById(idUser).orElse(null);
            
           
            username = u.getUsername();
            
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
            p.setNotiFromUsers(userLike);
            p.setUserCreateModel(u);
            posts.add(p);
            
            
            
        }
        return posts;
    }
    public ArrayList<PostModel> getAllMyPosts(int userID) throws SQLException{
        String sql ="SELECT * FROM tblPost WHERE userCreate = ?";
        PreparedStatement ps =sqlDB.con.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        ArrayList<PostModel> posts = new ArrayList<>(0);
        while(rs.next()){
            PostModel p  = new PostModel();
            int idPost = rs.getInt(1);
            if(idPost==1){
                continue;
            }
            String title = rs.getString(2);
            String description = rs.getString(3);
            
            int countLike =0;
            String sqlSelectLike = "SELECT * FROM tblPostLike WHERE idPost=?";
            PreparedStatement psSqlSlectLike = sqlDB.con.prepareStatement(sqlSelectLike);
            psSqlSlectLike.setInt(1, idPost);
            ResultSet reLike = psSqlSlectLike.executeQuery();
            
            ArrayList<Integer> userLike = new ArrayList<>(0);
            while(reLike.next()){
                countLike += 1;
                userLike.add(
                    Integer.valueOf(reLike.getInt("idUser"))
                );
                
            }
            
            
            String pictureDescription = rs.getString(4);
            String urlDesign = rs.getString(5);
            int idUser = rs.getInt(6);
            int price = rs.getInt(7);
            String username="";
            UserModel u = userRepo.findById(idUser).orElse(null);
            
           
            username = u.getUsername();
            
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
            p.setNotiFromUsers(userLike);
            p.setUserCreateModel(u);
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
        System.out.println(type);
        if(type.equalsIgnoreCase("like")){
            String sqlCheck = "SELECT * FROM tblPostLike WHERE idPost = ? AND idUser = ?";
            PreparedStatement psCheck = sqlDB.con.prepareStatement(sqlCheck);
            psCheck.setInt(2, userLiked.getId());
            psCheck.setInt(1, post.getIdPost());
            
            ResultSet rs = psCheck.executeQuery();
            
            if(rs.next()){
                // user da like 
                System.out.println("hereee ne");
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
                String sqlInsert = "DELETE FROM tblPostLike where `idPost`=? AND `idUser`=?";
                PreparedStatement psInsert = sqlDB.con.prepareStatement(sqlInsert);
                psInsert.setInt(1, post.getIdPost());
                psInsert.setInt(2, userLiked.getId());
                psInsert.executeUpdate();
                return "SUCCESS|OK";
            }
        }
        
    }

    public void createComment(Comment com) throws SQLException {
        String sql = "INSERT INTO tblComment(`idUserComment`,`idPost`,`timeCreate`,`comment`) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setInt(1, com.getUserComment().getId());
        ps.setInt(2, com.getPost().getIdPost());
        ps.setString(3, com.getTimeCreated());
        ps.setString(4, com.getComment());
        ps.executeUpdate();
    }

    public ArrayList<Comment> getAllComments(PostModel post) throws SQLException {
        String sql  = "SELECT * FROM tblComment WHERE idPost=?";
        PreparedStatement ps = sqlDB.con.prepareStatement(sql);
        ps.setInt(1, post.getIdPost());
        ResultSet rs =ps.executeQuery();
        ArrayList<Comment> c = new ArrayList<>(0);
        while(rs.next()){
            Comment m = new Comment();
            int id = rs.getInt("id");
            int idUser = rs.getInt("idUserComment");
            String comment = rs.getString("comment");
            String timeCreated = rs.getString("timeCreate");
            m.setPost(post);
            m.setUserComment(userRepo.findById(idUser).orElse(null));
            m.setComment(comment);
            m.setTimeCreated(timeCreated);
//            m.setUserComment(UserComment);
            c.add(m);
            
        }
        return c;
        
    }
}
