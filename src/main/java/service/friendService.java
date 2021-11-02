/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import lombok.RequiredArgsConstructor;
import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/friend/")
public class friendService {
    @Autowired
    private PasswordEncoder pe;
    @Autowired
    private UserRepository userRepo;
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtTokenUtil jwtTokenUtil;
    
    
    @PostMapping("addFriend")
    public ResponseEntity addFriend(
            @RequestParam(value="usernameAdded", required = false) String usernameAdded,
            @RequestHeader("Authorization") String header
    ){
        
        String token = header.split(" ")[1].trim();
        String username = jwtTokenUtil.getUsername(token);
        UserModel userCurrent = userRepo.findByUsername(username).orElse(null);
        
        
        UserModel userAdd = userRepo.findByUsername(usernameAdded).orElse(null);
        
        if(userAdd == null){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("user who you want add friend is not avaliable!!");
            
        }
        
        // userCurrent add friend with userAdd.
        return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).body("chua tich hop nha ");
        
        
        
        
        
        
        
    }
}
