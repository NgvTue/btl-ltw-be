/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import tokenAuthen.JwtTokenUtil;

/**
 *
 * @author tuenguyen
 */
@Service
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow/")
public class FollowService {
    @Autowired
    private PasswordEncoder pe;
    @Autowired
    private UserRepository userRepo;
    
    private final AuthenticationManager authenticationManager;
    @Autowired
    private NotiService notiService;
    private final JwtTokenUtil jwtTokenUtil;
    
    
    @PostMapping("followAnotherUser")
    public ResponseEntity addFollow(
            @RequestParam(value="usernameAdded", required = false) String usernameAdded,
            @RequestHeader("Authorization") String header
    ) throws SQLException{
        String token = header.split(" ")[1].trim();
        String username = jwtTokenUtil.getUsername(token);
        UserModel userCurrent = userRepo.findByUsername(username).orElse(null);
        UserModel userWantFollow = userRepo.findByUsername(usernameAdded).orElse(null);
        String curentTime = LocalDateTime.now().toString();
        String status=userRepo.followAction(
                userCurrent, // 11 
                userWantFollow,// 4
                curentTime
        );
        
        if(status.contains("FAILED")){
            System.out.println(status);
            return ResponseEntity.status(404).body(status);
        }
        else
        {            
            // add noti for userWantFollow that someone follow they.
            notiService.followAction(userCurrent,userWantFollow, token);
            return ResponseEntity.ok().body("SUCCESS");
        }

    }
    
    @GetMapping("getAllFollowerOfUser") // tra ve danh sach cac user follower user hien tai
    public ResponseEntity changeProfilePictureAPI(
        
        @RequestParam(value="username",required=false) String username
       
    ) throws SQLException
    {
        UserModel us = userRepo.findByUsername(username).orElse(null);
        if(us==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user k ton tai");
        }
        ArrayList<UserModel> usx = userRepo.getAllFollowers(us);
        
        return ResponseEntity.ok().body(usx);
    }
}
