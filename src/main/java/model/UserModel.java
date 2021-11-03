/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tuenguyen
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author tuenguyen
 */

public class UserModel implements UserDetails, Serializable{
    private String username;
    private String password;
    private String email;
    
    private String role;
    
    
    // keys
    private int id;
    private int profileID;
       
    
    private String fullname;
    private String address;
    private String dob;

    private String urlProfilePicture;
    private String urlBackgroundPicture;

    public UserModel(String username, String password, String email, String role, int id, int profileID, String fullname, String address, String dob, String urlProfilePicture, String urlBackgroundPicture, String phone, String createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.id = id;
        this.profileID = profileID;
        this.fullname = fullname;
        this.address = address;
        this.dob = dob;
        this.urlProfilePicture = urlProfilePicture;
        this.urlBackgroundPicture = urlBackgroundPicture;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public String getUrlProfilePicture() {
        return urlProfilePicture;
    }

    public void setUrlProfilePicture(String urlProfilePicture) {
        this.urlProfilePicture = urlProfilePicture;
    }

    public String getUrlBackgroundPicture() {
        return urlBackgroundPicture;
    }

    public void setUrlBackgroundPicture(String urlBackgroundPicture) {
        this.urlBackgroundPicture = urlBackgroundPicture;
    }
    
    
    
    public UserModel(String username, String password, String email, String role, int id, int profileID, String fullname, String address, String dob, String phone, String createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.id = id;
        this.profileID = profileID;
        this.fullname = fullname;
        this.address = address;
        this.dob = dob;
        this.phone = phone;
        this.createdAt = createdAt;
    }
    private String phone;
    private String createdAt;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel() {
    }

    public UserModel(String username, String password, String email, String role, int id, int profileID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.id = id;
        this.profileID = profileID;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

  
   

    
    
}

