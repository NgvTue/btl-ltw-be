/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author tuenguyen
 */
public class PostModel implements Serializable{
    private String userCreate;
    private String titlePost;
    private String descriptionPost;
    private String descriptionPicture;
    private String urlDesign;
    private ArrayList<String> tags;

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    private int loveCount;

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public String getDescriptionPost() {
        return descriptionPost;
    }

    public void setDescriptionPost(String descriptionPost) {
        this.descriptionPost = descriptionPost;
    }

    public String getDescriptionPicture() {
        return descriptionPicture;
    }

    public void setDescriptionPicture(String descriptionPicture) {
        this.descriptionPicture = descriptionPicture;
    }

    public String getUrlDesign() {
        return urlDesign;
    }

    public void setUrlDesign(String urlDesign) {
        this.urlDesign = urlDesign;
    }

    public int getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(int loveCount) {
        this.loveCount = loveCount;
    }
    
    public PostModel() {
    }

    public PostModel(String userCreate, String titlePost, String descriptionPost, String descriptionPicture, String urlDesign, int loveCount) {
        this.userCreate = userCreate;
        this.titlePost = titlePost;
        this.descriptionPost = descriptionPost;
        this.descriptionPicture = descriptionPicture;
        this.urlDesign = urlDesign;
        this.loveCount = loveCount;
    }
    
    
    
}
