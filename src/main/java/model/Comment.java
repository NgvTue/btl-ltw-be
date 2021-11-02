/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author tuenguyen
 */
public class Comment {
    private PostModel post;
    private UserModel UserComment;
    private String comment;
    private String timeCreated;

    public Comment() {
    }

    public Comment(PostModel idPost, UserModel idUserComment, String comment, String timeCreated) {
        this.post = idPost;
        this.UserComment = idUserComment;
        this.comment = comment;
        this.timeCreated = timeCreated;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public UserModel getUserComment() {
        return UserComment;
    }

    public void setUserComment(UserModel UserComment) {
        this.UserComment = UserComment;
    }

   

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    
    
}
