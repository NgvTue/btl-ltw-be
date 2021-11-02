/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public ResponseEntity addFriend(
            @RequestParam(value="usernameAdded", required = false) String usernameAdded,
            @RequestHeader("Authorization") String header
    ) throws SQLException{
        String token = header.split(" ")[1].trim();
        String username = jwtTokenUtil.getUsername(token);
        UserModel userCurrent = userRepo.findByUsername(username).orElse(null);
        UserModel userWantFollow = userRepo.findByUsername(usernameAdded).orElse(null);
        String curentTime = LocalDate.now().toString();
        String status=userRepo.followAction(
                userCurrent,
                userWantFollow,
                curentTime
        );
        
        if(status.contentEquals("FAILED")){
            System.out.println(status);
            return ResponseEntity.status(404).body(status);
        }
        else
        {            
            // add noti for userWantFollow that someone follow they.
            notiService.followAction(userCurrent, userCurrent, token);
            return ResponseEntity.ok().body("SUCCESS");
        }

    }
}
