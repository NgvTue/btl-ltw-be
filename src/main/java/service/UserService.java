/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import Reponse.UserResponse;
import configuration.FileStorageProperties;
import java.io.IOException;
import repository.UserRepository;
import static java.lang.String.format;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Response;
import lombok.RequiredArgsConstructor;
import model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import tokenAuthen.JwtTokenUtil;
import model.UserModel;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.PostRepository;

/**
 *
 * @author tuenguyen
 */
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class UserService implements UserDetailsService{
    @Autowired
    private MailService mailService;
    @Autowired
    private PostService postService;
    @Autowired
    private PasswordEncoder pe;
    
    @Autowired
    private UserRepository userRepo;
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtTokenUtil jwtTokenUtil;
//    private final UserViewMapper userViewMapper; 
    
    @PostMapping("login")
    public ResponseEntity checkUser(@RequestBody UserModel user) {
         
        try {
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                    )
                );
            
            UserModel us = (UserModel)authenticate.getPrincipal();
            
            String jwt = jwtTokenUtil.generateAccessToken(user);
            UserResponse re = new UserResponse();
            Map<String, String> map = new HashMap<String, String>();
            user=userRepo.findByUsername(user.getUsername()).orElse(null);
            map.put("token",jwt);
            map.put("userID", String.valueOf(user.getId()));
//            re.setData(jwt);
            return ResponseEntity.ok().body(map);
            
        }
        catch (BadCredentialsException ex) {
            System.out.println("here bug");
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        

    }
    @PostMapping("registeruser")
    public ResponseEntity register(
         
            @RequestParam(value="urlProfile",required=false) MultipartFile filePicture,
            @RequestParam(value="urlBackground", required=false) MultipartFile design,
            @RequestParam(value="username",required=false) String username,
            @RequestParam(value="password",required=false) String password,
            @RequestParam(value="email",required=false) String email,
            @RequestParam(value="role",required=false) String role,
            @RequestParam(value="fullname",required=false) String fullname,
            @RequestParam(value="address",required=false) String address,
            @RequestParam(value="dob",required=false) String dob,
            @RequestParam(value="phone",required=false) String phone
            
            
    ) throws IOException{
        
       
        String createdAt = LocalTime.now().toString();
        UserModel user = new UserModel();
        user.setAddress(address);
        user.setCreatedAt(createdAt);
        user.setDob(dob);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhone(phone);
        
        if(filePicture !=null)
        {
            String file =postService.storeFile(filePicture,"users"); // save to users 
        
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/database/")
                    .path(file)
                    .toUriString();

            user.setUrlProfilePicture(fileDownloadUri);

            file = postService.storeFile(design,"users");
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/database/")
                    .path(file)
                    .toUriString();
            user.setUrlBackgroundPicture(fileDownloadUri);
        }
        
//        user.setUrlBackgroundPicture(urlBackgroundPicture);
        // save image before
        String status = userRepo.RegistUser(user);
        if(!status.contains("SUCESS" )){
//            ResponseUser rs  = new ResponseUser(null,status);
//            rs.setStatus("failed");
//            rs.setStatusCode(404);
            UserResponse rs = new UserResponse();
            rs.setStatus("FAILED");
            rs.setMess(status);
            rs.setData(user);
            return ResponseEntity.status(404).body(rs);
        }
        UserResponse rs  = new UserResponse();
        user.setPassword(null);
        rs.setData(user);
        return ResponseEntity.ok().body(rs);
    }
    
    @GetMapping("/detail/{idUser}")
    public ResponseEntity getDetailUserAPI(@PathVariable("idUser") int idUser){
         UserModel us = userRepo.findById(idUser).orElse(null);
         if(us == null){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user k ton tai");
         }
         us.setPassword(null);
         return ResponseEntity.ok().body(us);
    }
    
        
       
     
    
    @PostMapping("changePassword")
    public ResponseEntity changePasswordAPI(@RequestHeader("Authorization") String header, @RequestBody UserModel user){
        if(header == null){
            System.out.println("here1111");
        }
        System.out.println(header);
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        String password = jwtTokenUtil.getPasswordFromToken(token);
        password = pe.encode(password);
        UserModel us = userRepo.findByUsername(username).orElse(null);
        if(user.getPassword() != null && password != pe.encode(user.getPassword())){
            us.setPassword(pe.encode(user.getPassword()));
        }
        if(user.getEmail() !=null && !user.getEmail().equals(us.getEmail())){
            us.setEmail(user.getEmail());
        }
        if(user.getDob()!=null){
            us.setDob(user.getDob());
        }
        System.out.println(us.getEmail());
        String status=userRepo.setUser(us);
        return ResponseEntity.ok().body(status);
    }
    @PostMapping("testToken")
    public ResponseEntity registerfake(@RequestHeader("Authorization") String header, @RequestBody UserModel user){
        return ResponseEntity.ok().body("chỉ có token mới vào được  đây");
    }
    @PostMapping("forgetPassword")
    public ResponseEntity forgetPasswordAPI(@RequestBody UserModel user){
        String username=user.getUsername();
        String email = user.getEmail();
        if(email == null){
            UserResponse ur = new UserResponse();
            ur.setStatus("FAILED");
            ur.setMess("forget password by email");
            return ResponseEntity.status(404).body(ur);
        }
        
        UserModel us = userRepo.findByUsername(username).orElse(null);
        if(us == null){
            UserResponse ur = new UserResponse();
            ur.setStatus("FAILED");
            ur.setMess("không tồn tại username");
            return ResponseEntity.status(404).body(ur);
        }
        UserResponse ur = new UserResponse();
        String forgetPass = us.getPassword();
        Mail mail = new Mail();
        mail.setMailFrom("thangquyvanthao2000@gmail.com");
        mail.setMailTo(us.getEmail());
        mail.setMailSubject("Forget Password - Hahahihi");
//        mail.setMailContent("Mật khẩu của bạn là: " + forgetPass +"\n");
//        mail.setMailContent("Truy cập theo link này để đổi mật khẩu ");
        System.out.println(us.getEmail());
        String message = "Truy cập vô đây để nhận mật khẩu mới, vui lòng đăng nhập và thay đổi mật khẩu sau khi nhận được:\n" + "http://localhost:3000/forget-password/?username=" + username + "&encodePass=" + forgetPass;
        mail.setMailContent(message);
        mailService.sendEmail(mail);
        ur.setMess("Hãy kiểm tra email của bạn!");
        return ResponseEntity.ok().body(ur);
    }
    @PostMapping("changePasswordByEmail")
    public ResponseEntity changePasswordByEmailAPI(
          @RequestParam(value="username") String username,
          @RequestParam(value="encodePass", required=false) String encodePass
    ){
        System.out.println("user name " + username);
        UserModel us = userRepo.findByUsername(username).orElse(null);
        if(us == null){
            UserResponse ur = new UserResponse();
            ur.setStatus("FAILED");
            ur.setMess("không tồn tại username");
            return ResponseEntity.status(404).body(ur);
            
            
        }
        // set new password for user;
        int r =new Random().nextInt(10000);
        String password = "new_password_" + r;
        us.setPassword(pe.encode(password));
        userRepo.setUser(us);
        return ResponseEntity.ok().body(password);
    }
    
    @PostMapping("changeProfile")
    public ResponseEntity changeProfilePictureAPI(
        @RequestHeader("Authorization") String header,
        @RequestParam(value="urlProfile",required=false) MultipartFile filePicture,
        @RequestParam(value="urlBackground", required=false) MultipartFile fileBackground,
        @RequestParam(value="fullname", required=false) String fullname,
        @RequestParam(value="email", required=false) String email,
        @RequestParam(value="address", required=false) String address,
        @RequestParam(value="phone", required=false) String phone
    ) throws IOException{
        String token = header.split(" ")[1].trim();
        System.out.println(token);
        String username = jwtTokenUtil.getUsername(token);
        String password = jwtTokenUtil.getPasswordFromToken(token);
        password = pe.encode(password);
        UserModel us = userRepo.findByUsername(username).orElse(null);
        if(address != null){
            us.setAddress(address);
        }
        if(email != null){
            us.setEmail(email);
        }
        if(fullname != null){
            us.setFullname(fullname);
        }
        if(phone != null){
            us.setPhone(phone);
        }
        if(filePicture != null){
            // save filePicture
            String file =postService.storeFile(filePicture,"users"); // save to users 
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/database/")
                    .path(file)
                    .toUriString();
            us.setUrlProfilePicture(fileDownloadUri);
        }
        if(fileBackground !=null){
        // save fileBackground
            String file = postService.storeFile(fileBackground,"users");
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/database/")
                    .path(file)
                    .toUriString();
            us.setUrlBackgroundPicture(fileDownloadUri);
        }
        String status=userRepo.setUser(us);
        return ResponseEntity.ok().body(us);
        
        
    }
    
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username))
                );
    }
    
    

    

}
