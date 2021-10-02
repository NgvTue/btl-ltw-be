/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btlltw.moduleUser;

import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuenguyen
 */
@Service
@RestController
@RequestMapping("/api/user/")
public class UserService {
    
    
    @Autowired
    private UserRepository ur;
    
    
    @GetMapping("/checkuser")
    public ResponseEntity checkUser(@RequestBody UserModel user) {
//        System.out.println(user.getPassword());
        UserModel us = ur.getUser(user.getName(),user.getPassword());
        if (us == null){
            ResponseUser rs = new ResponseUser(null,"ko tim thay ac");
            rs.setStatus("404");
            rs.setStatusCode(404);
            return ResponseEntity.status(404).body(rs);
        }
        ResponseUser rs = new ResponseUser(us,"ok");
            rs.setStatus("200");
            rs.setStatusCode(200);
        return ResponseEntity.ok().body(rs);
//        todoList.add(todo);
        // Trả về response với STATUS CODE = 200 (OK)
        // Body sẽ chứa thông tin về đối tượng todo vừa được tạo.
//        return ResponseEntity.ok().body(todo);
    }
    @PostMapping("/registeruser")
    public ResponseEntity register(@RequestBody UserModel user){
        String status = ur.RegistUser(user.getName(), user.getPassword());
        if(status.contains("FAILED" )){
            ResponseUser rs  = new ResponseUser(null,status);
            rs.setStatus("djaskldjkas");
            rs.setStatusCode(0);
            return ResponseEntity.ok().body(rs);
        }
        ResponseUser rs  = new ResponseUser(user,status);
        return ResponseEntity.ok().body(rs);
    }
    
    
    @PutMapping("/changepassword/{username}")
    public ResponseEntity changePassword(
            
            
            @PathVariable(name = "username") String username,
            
            @RequestBody UserModel user
    ){
        
        
        String status = ur.RegistUser(user.getName(), user.getPassword());
        if(status.contains("FAILED" )){
            ResponseUser rs  = new ResponseUser(null,status);
            rs.setStatus("djaskldjkas");
            rs.setStatusCode(0);
            return ResponseEntity.ok().body(rs);
        }
        ResponseUser rs  = new ResponseUser(user,status);
        return ResponseEntity.ok().body(rs);
    }

}
