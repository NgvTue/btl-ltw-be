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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import model.PictureModel;
import model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
            @RequestParam("picture") MultipartFile filePicture,
            
            @RequestParam(value="titlePost",required = false) String titlePost,
            @RequestParam(value="descriptionPost",required = false) String descriptionPost,
            @RequestParam(value="descriptionPicture",required = false) String descriptionPicture,
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
        postModel.setDescriptionPicture(descriptionPicture);
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
        
//        ArrayList<String> tags = new ArrayList<>(0);
//        for (String tag : tags){
//            
//        }
        
        String file = storeFile(filePicture);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/database/")
                .path(file)
                .toUriString();
        postModel.setUrlDesign(fileDownloadUri);
        String status = postRepo.createPost(postModel);
        if (status.contains("FAILED")){
            return ResponseEntity.status(404).body(status);
        }
        
        
        return ResponseEntity.ok().body(postModel);
        
        
        

    }
    @GetMapping("getAllPosts")
    public ResponseEntity getAllPosts(
    
            
        
    ) throws SQLException{
        ArrayList<PostModel> posts = postRepo.getAllPosts();
        
        return ResponseEntity.ok().body(posts);
        
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
            Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileName1;
        
    }
}
