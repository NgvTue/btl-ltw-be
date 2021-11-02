/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import configuration.FileStorageProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import model.Comment;
import model.NotiModel;
import model.PictureModel;
import model.PostModel;
import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.PostRepository;
import repository.UserRepository;
import tokenAuthen.JwtTokenUtil;
import org.springframework.http.MediaType;
import repository.NotiRepository;
/**
 *
 * @author tuenguyen
 */
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/")
public class PostService {
    private final Path fileStorageLocation;
    @Autowired
    private  PostRepository postRepo;
    
    @Autowired
    private NotiRepository notiRepo;
    @Autowired
    private UserRepository userRepo;
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    public PostService(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);   
        jwtTokenUtil = new JwtTokenUtil();
    }
//    @GetMapping("resources/*")
    @PostMapping("addPost")
    public ResponseEntity addPost(
            @RequestParam("urlPicture") MultipartFile filePicture,
            @RequestParam(value="urlDesign", required=false) MultipartFile design,
            
            @RequestParam(value="titlePost",required = false) String titlePost,
            @RequestParam(value="descriptionPost",required = false) String descriptionPost,
            
            @RequestParam(value="tags",required = false) ArrayList<String> tags,
            @RequestParam(value="price",defaultValue ="0",required = false) int price,
            
            @RequestHeader("Authorization") String header
            ) throws IOException, SQLException  {
        
        System.out.println(header);
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        
        
        PostModel postModel = new PostModel();
        postModel.setUserCreate(username);
        
        postModel.setDescriptionPost(descriptionPost);
        postModel.setLoveCount(0);
        postModel.setTitlePost(titlePost);
        if(tags !=null)
            postModel.setTags(tags);
        else
        {
            postModel.setTags(new ArrayList<>(0));
        }
        
        postModel.setPrice(price);
        

        
        String file = storeFile(filePicture);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/database/")
                .path(file)
                .toUriString();
        postModel.setUrlPicture(fileDownloadUri);
        
        file = storeFile(design);
        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/database/")
                .path(file)
                .toUriString();
        postModel.setUrlDesign(fileDownloadUri);
        String status = postRepo.createPost(postModel);
        if (status.contentEquals("FAILED")){
            return ResponseEntity.status(404).body(status);
        }
        
        // notifi for all user
        return ResponseEntity.ok().body(postModel);
        
        
        

    }
    // api/post/1 
    @GetMapping("{idPost}")
    public ResponseEntity getDetail(
            @PathVariable("idPost") int id
            
        
    ) throws SQLException{
        PostModel post = new PostModel();
        post.setIdPost(id);
        post = postRepo.getDetailPost(post);
        return ResponseEntity.ok().body(post);
        
    }
    
    @GetMapping("getAllPosts")
    public ResponseEntity getAllPosts(
            
            
        
    ) throws SQLException{
        ArrayList<PostModel> posts = postRepo.getAllPosts();
        
        return ResponseEntity.ok().body(posts);
        
    }
   
    
    @PostMapping("commentAction")
    public ResponseEntity commentActionAPI(
            @RequestHeader("Authorization") String header,
            
            @RequestParam(value="postID", required = true) int postID,
            @RequestParam(value="userID", required = true) int userCommentID,
            @RequestParam(value="comment", required = true) String comment
            
            
            ) throws SQLException{
        
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        UserModel userCurrent = userRepo.findByUsername(username).orElse(null);
        UserModel userComment = userRepo.findById(userCommentID).orElse(null);
        if(userComment == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("userID comment required");
        }
        
        Comment com = new Comment();
        PostModel post =new PostModel();
        post.setIdPost(postID);
        post = postRepo.getDetailPost(post);
        com.setPost(post);
//        UserModel user = userRepo.findById(userCommentID);
        com.setUserComment(userComment);
        com.setComment(comment);
        com.setTimeCreated(LocalDateTime.now().toString());
        
        postRepo.createComment(com);
        // userCurrent 
        
        // notifi for user
        
        NotiModel no = new NotiModel();
        // noifi for owner of post
        no.setFrom(userComment);
        no.setTo(userCurrent);
        no.setIsViewed(0);
        no.set
        return ResponseEntity.ok().body(com);
    }
    
    @GetMapping("getAllCommentByPost")
    public ResponseEntity getAllCommentPost(
            @RequestHeader("Authorization") String header,
            @RequestParam(value="postID", required = true) int postID) throws SQLException{
        
        
        PostModel post =new PostModel();
        post.setIdPost(postID);
        post = postRepo.getDetailPost(post);
        ArrayList<Comment> com = postRepo.getAllComments(
             post
        );
        return ResponseEntity.ok().body(com);
    } 
    
    
    @GetMapping("getAllNoti")
    public ResponseEntity getAllNoti(
    @RequestHeader("Authorization") String header) throws SQLException{
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        UserModel user = userRepo.findByUsername(username).orElse(null);
        int userID = user.getId();
        ArrayList<NotiModel> notis = notiRepo.getAllNotiOfUser( userRepo.findById(userID).orElse(null) );
        return ResponseEntity.ok().body(notis);
    }
    @PostMapping("likeaction")
    public ResponseEntity likeAction(
            @RequestParam(value="postID",required = true) String postx,
            @RequestParam(value="actionType", required = false) String actionType,
            @RequestHeader("Authorization") String header) throws SQLException{ // like or dis like
        
        if(actionType == null){
            actionType="like";
        }
        int postID = Integer.parseInt(postx);
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        UserModel userLiked = userRepo.findByUsername(username).orElse(null);
        System.out.println("here");
        int userID = userLiked.getId();
//        int userID=Integer.parseInt(jwtTokenUtil.getUserId(token));
        PostModel post = new PostModel();
        post.setIdPost(postID);
        post=postRepo.getDetailPost(post);
        String userNameCreated = post.getUserCreate();
        
        
       
        UserModel userRec  = new UserModel();
        userRec.setId(post.getIdUserCreated());
        userRec = userRepo.findById(userRec.getId()).orElse(null);
        
        
        String status = postRepo.updateLikePost(post, userLiked, actionType);
        System.out.println(status);
        if( status.contains("FAILED") ){
            System.out.println("di vao day di");
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(status);
        }
        
        // update notification here
        
        NotiModel noti = new NotiModel();
        
        noti.setFrom(userLiked);
        noti.setTo(userRec);
        String description = userLiked.getFullname() + " liked " + post.getTitlePost() + " FROM " + userRec.getFullname();
        String urlLink = post.getUrlPicture();
        String type = "liked";
        noti.setDescription(description);
        noti.setType(type);
        noti.setUrlNotification(urlLink);
        noti.setPost(post);
        
        
        notiRepo.createNoti(noti);
        
        return ResponseEntity.ok().body("update succes");

    }
    


     public String storeFile(MultipartFile file) throws IOException, IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
         System.out.println(fileName);
        String fileName1 = "pictures/"  + fileName;
            // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName1);
        
        
        
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileName1;
        
    }
}
