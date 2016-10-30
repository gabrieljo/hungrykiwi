package app.me.hungrykiwi.model;

import com.google.gson.annotations.SerializedName;

import app.me.hungrykiwi.model.user.User;

/**
 * Created by user on 10/15/2016.
 */

public class Comment {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("post_id")
    private int postId;
    @SerializedName("restr_id")
    private int restrId;
    @SerializedName("comment")
    private String comment;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("rate")
    private float rate;

    private User user;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestrId() {
        return restrId;
    }

    public void setRestrId(int restrId) {
        this.restrId = restrId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
                "COMMENT------------------------"+
                "\nUSER_ID : "+userId+
                "\nRESTR_ID : "+restrId+
                "\nCOMMENT : "+comment+
                "\nUPDATED_AT : "+updatedAt
        );
        if(this.user != null) {
            builder.append(this.user.toString());
        }
        return builder.toString();
    }
}
