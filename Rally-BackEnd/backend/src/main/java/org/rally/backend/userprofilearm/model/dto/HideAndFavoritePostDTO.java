package org.rally.backend.userprofilearm.model.dto;

public class HideAndFavoritePostDTO {

    public String postType;
    public Integer hidePostId;
    public Integer userId;

    public HideAndFavoritePostDTO(String postType, Integer hidePostId, Integer userId) {
        this.postType = postType;
        this.hidePostId = hidePostId;
        this.userId = userId;
    }

    public HideAndFavoritePostDTO() {
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
