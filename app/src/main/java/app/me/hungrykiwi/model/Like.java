package app.me.hungrykiwi.model;

import com.google.gson.annotations.SerializedName;

import app.me.hungrykiwi.model.user.User;

/**
 * contains info about who like post or recipe
 * Created by user on 10/16/2016.
 */
public class Like {
    User user;
    @SerializedName("updated_at")
    private String updatedAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        String content = "Like--------------------------\n"
                    +this.updatedAt;
        if(user != null) content.contains(user.toString());
        return content;
    }
}
