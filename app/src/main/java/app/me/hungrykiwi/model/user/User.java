package app.me.hungrykiwi.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * model for a user
 * Created by peterlee on 2016-09-13.
 */
public class User {
    private static User appUser; // app user
    @SerializedName("id")
    private int id; // email
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
    @SerializedName("provider")
    private int provider; // provider

    private String name;


    private Restr restr;
    public static User getAppUser() {
        if(User.appUser == null)
            appUser = new User();
        return User.appUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }

    public Restr getRestr() {
        return restr;
    }

    public void setRestr(Restr restr) {
        this.restr = restr;
    }

    public static void setAppUser(User user) {
        User.appUser = user;
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
        StringBuilder builder = new StringBuilder();
        builder.append("\nUSER------------------------------\n"+
                "EMAIL : "+email
                +"\nID : "+id
                +"\nProvider : "+provider
                +"\nLAST NAME : "+lName
                +"\nFIRST NAME : "+fName
                +"\nNAME : "+name
                +"\nIS RESTRAURANT : "+isRestr
                +"\nIMAGE PATH : "+imgPath
                +"\nCOVER IMAGE PATH : "+imgCover+"\n");

        if(this.restr != null) {
            builder.append(this.restr.toString());
        }
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
