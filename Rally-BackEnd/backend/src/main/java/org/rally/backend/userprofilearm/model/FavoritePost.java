package org.rally.backend.userprofilearm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FavoritePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String postType;
    private Integer hidePostId;
    private Integer userId;

    public FavoritePost(String postType, Integer hidePostId, Integer userId) {
        this.postType = postType;
        this.hidePostId = hidePostId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getHidePostId() {
        return hidePostId;
    }

    public void setHidePostId(Integer hidePostId) {
        this.hidePostId = hidePostId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
