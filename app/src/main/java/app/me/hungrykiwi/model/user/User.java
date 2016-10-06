package app.me.hungrykiwi.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * model for a user
 * Created by peterlee on 2016-09-13.
 */
public class User {
    private static User appUser; // app user

    @SerializedName("email")
    private String email; // email
    @SerializedName("first_name")
    private String fName; // name
    @SerializedName("last_name")
    private String lName; // name
    @SerializedName("is_restr")
    private int isRestr;
    @SerializedName("img")
    private String imgPath; // image url
    @SerializedName("cover_img")
    private String imgCover; // image url
    private String name;




    public static User getAppUser() {
        if(User.appUser == null)
            appUser = new User();
        return User.appUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getIsRestr() {
        return isRestr;
    }

    public void setIsRestr(int isRestr) {
        this.isRestr = isRestr;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "EMAIL : "+email
                +"\nLAST NAME : "+lName
                +"\nLAST NAME : "+fName
                +"\nIS RESTRAURANT : "+isRestr
                +"\nIMAGE PATH : "+imgPath
                +"\nCOVER IMAGE PATH : "+imgCover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
