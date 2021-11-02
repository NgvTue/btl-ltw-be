/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.logging.Logger;

/**
 *
 * @author tuenguyen
 */
public class NotiModel {
    private int id;
    private PostModel post;
    private UserModel from;
    private UserModel to;
    private String type;
    private String description;
    private String urlNotification; // if admin created some notification to user: ... or another user liked your post, ect

    public NotiModel(int id, PostModel post, UserModel from, UserModel to, String type, String description, String urlNotification) {
        this.id = id;
        this.post = post;
        this.from = from;
        this.to = to;
        this.type = type;
        this.description = description;
        this.urlNotification = urlNotification;
    }

    public NotiModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public UserModel getFrom() {
        return from;
    }

    public void setFrom(UserModel from) {
        this.from = from;
    }

    public UserModel getTo() {
        return to;
    }

    public void setTo(UserModel to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlNotification() {
        return urlNotification;
    }

    public void setUrlNotification(String urlNotification) {
        this.urlNotification = urlNotification;
    }
    
    
    
    
    
}
