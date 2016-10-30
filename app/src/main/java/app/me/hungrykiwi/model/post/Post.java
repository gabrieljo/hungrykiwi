package app.me.hungrykiwi.model.post;

import com.google.gson.annotations.SerializedName;

import app.me.hungrykiwi.model.user.User;

/**
 * SNS
 * Created by peterlee on 2016-09-13.
 */
public class Post {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("restr_id")
    private int restr_id;
    @SerializedName("content")
    private String content;
    @SerializedName("like_num")
    private int likeNum;
    @SerializedName("comment_num")
    private int commentNum;
    @SerializedName("is_img")
    private int isImg;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    private User user;
    private String[] imgs;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestr_id() {
        return restr_id;
    }

    public void setRestr_id(int restr_id) {
        this.restr_id = restr_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getIsImg() {
        return isImg;
    }

    public void setIsImg(int isImg) {
        this.isImg = isImg;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nPOST------------------------------\n"+
                "ID : "+id
                +"\nUSER_ID : "+userId
                +"\nCONTENT : "+content
                +"\nLIKE_NUM : "+likeNum
                +"\nCOMMENT_NUM : "+commentNum
                +"\nIS_IMAGE : "+isImg
                +"\nUPDATED_AT : "+updatedAt+"\n");
        if(this.imgs != null && this.imgs.length != 0) {
            int count = this.imgs.length;
            for(int i =0 ; i < count; i++) {
                builder.append("\nIMAGE : "+this.imgs[i]);
            }
        }
        if(this.user != null) {
            builder.append(this.user.toString());
        }
        return builder.toString();
    }
}
