package app.me.hungrykiwi.model;

import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import app.me.hungrykiwi.model.user.User;

/**
 * recipe DAO
 * Created by peterlee on 2016-09-13.
 */
public class Recipe{
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private int type;
    @SerializedName("is_vegi")
    private int is_vegi;
    @SerializedName("require_min")
    private int required_min;
    @SerializedName("comment_num")
    private int comment_num;
    @SerializedName("rating")
    private float rating;
    @SerializedName("ingredient")
    private String ingre;
    @SerializedName("content")
    private String content;
    @SerializedName("img")
    private String img;
    @SerializedName("is_img")
    private int is_img;
    @SerializedName("view")
    private int view;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;


    private User user;

    private enum Type {
        CHINESE,
        JAPANESE
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_vegi() {
        return is_vegi;
    }

    public void setIs_vegi(int is_vegi) {
        this.is_vegi = is_vegi;
    }

    public int getRequired_min() {
        return required_min;
    }

    public void setRequired_min(int required_min) {
        this.required_min = required_min;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getIngre() {
        return ingre;
    }

    public void setIngre(String ingre) {
        this.ingre = ingre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIs_img() {
        return is_img;
    }

    public void setIs_img(int is_img) {
        this.is_img = is_img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
                "\nRecipe-----------------------"+
                "\nID : "+this.id+
                "\nNAME : "+this.name+
                "\nTYPE : "+this.type+
                "\nIS_VEGI : "+this.is_vegi+
                "\nVIEW : "+this.view+
                "\nREQUIRED_MIN : "+this.required_min+
                "\nCOMMENT_NUM : "+this.comment_num+
                "\nINGREDIENT : "+this.ingre+
                "\nCONTENT : "+this.content+
                "\nIMG : "+this.img+
                "\nIS_IMG : "+this.is_img+
                "\nCREATED_AT : "+this.created_at+
                "\nUPDATED_AT : "+this.updated_at
        );
        if(user != null) builder.append(user.toString());
        return builder.toString();
    }
}