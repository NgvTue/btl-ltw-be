/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import Reponse.UserResponse;
import repository.UserRepository;
import static java.lang.String.format;
import javax.xml.ws.Response;
import lombok.RequiredArgsConstructor;
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
            re.setData(jwt);
            return ResponseEntity.ok()
                    .body(re);
            
        }
        catch (BadCredentialsException ex) {
            System.out.println("here bug");
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        

    }
    @PostMapping("registeruser")
    public ResponseEntity register(@RequestBody UserModel user){
        
        
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
    


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", username))
                );
    }

    

}
