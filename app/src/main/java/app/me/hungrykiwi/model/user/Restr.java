package app.me.hungrykiwi.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Restraurant info
 * Created by user on 10/3/2016.
 */
public class Restr {

    @SerializedName("id")
    private int id;
    @SerializedName("zomato_id")
    private int zomato_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("name")
    private String name;
    @SerializedName("intro")
    private String intro;
    @SerializedName("phone")
    private String phone;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double longitude;
    @SerializedName("is_img")
    private int is_img;
    @SerializedName("menu_table")
    private String menuTable;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("address")
    private String address;
    @SerializedName("locality")
    private String locality;
    @SerializedName("city")
    private String city;
    @SerializedName("cuisines")
    private String cuisine;
    @SerializedName("thumb")
    private String img;
    @SerializedName("aggregate_rating")
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getZomato_id() {
        return zomato_id;
    }

    public void setZomato_id(int zomato_id) {
        this.zomato_id = zomato_id;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getIs_img() {
        return is_img;
    }

    public void setIs_img(int is_img) {
        this.is_img = is_img;
    }

    public String getMenuTable() {
        return menuTable;
    }

    public void setMenuTable(String menuTable) {
        this.menuTable = menuTable;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "\nRestraurant------------------------------"
                +"\nID : "+id
                +"\nUSER_ID : "+user_id
                +"\nNAME : "+name
                +"\nIMAGE : "+img
                +"\nINTRO : "+intro
                +"\nADDRESS : "+address
                +"\nPHONE : "+phone
                +"\nMOBILE : "+mobile
                +"\nLAT : "+lat
                +"\nLONG : "+longitude
                +"\nIS_IMG : "+is_img
                +"\nMENU_TABLE : "+menuTable
                +"\nCREATED_AT : "+created_at
                +"\nUPDATED_AT : "+updated_at+"\n";
    }
}
