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
    
    
    private int idPost;

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }
    private String userCreate;
    private String titlePost;
    private String descriptionPost;
    private String urlPicture;
    private String urlDesign;
    private int price = 0;
    private ArrayList<String> tags;
    private int loveCount;
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
   

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

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String descriptionPicture) {
        this.urlPicture = descriptionPicture;
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
        this.urlPicture = descriptionPicture;
        this.urlDesign = urlDesign;
        this.loveCount = loveCount;
    }
    
    
    
}
