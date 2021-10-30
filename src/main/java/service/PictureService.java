/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Reponse.UserResponse;
import configuration.FileStorageProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import model.PictureModel;
import model.UserModel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.PictureRepository;
import repository.UserRepository;

/**
 *
 * @author tuenguyen
 */
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture/")
public class PictureService {
    
    private final Path fileStorageLocation;
    @Autowired
    private PictureRepository picRepo;
    @Autowired
    public PictureService(FileStorageProperties fileStorageProperties) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
        
    }
    public String storeFile(MultipartFile file) throws IOException, IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        
            
            // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileName;
        
    }
    
    public Resource loadFileAsResource(String fileName) throws Exception {
        
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if(resource.exists()) 
                return resource;
            else
            {
                throw new Exception("file not found "  + fileName);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PostMapping("addpicture")
    public ResponseEntity addPicture(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) throws IOException {
        String fileName = storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        PictureModel test = new PictureModel();
        test.setDescription("test decription");
        test.setTitle(title);
        return ResponseEntity.ok().body(fileDownloadUri);
                    
        

    }
}
