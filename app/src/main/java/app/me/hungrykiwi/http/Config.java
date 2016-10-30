package app.me.hungrykiwi.http;

/**
 * configuration for connecting to database
 * Created by user on 10/3/2016.
 */
public class Config {
    public static String baseUrl  = ""; // base url
    public static String zomatoBaseUrl  = ""; // base url
    public static final boolean isAlive = false; // testing or real server?
    public static final int pageLimit = 10; // post pagination limit


    static {
        if(isAlive == true) {
            // TODO : When connected with real server.

            zomatoBaseUrl = "https://developers.zomato.com/api/v2.1/"; // zomato api
        } else {
            baseUrl = "http://192.168.1.17:8000/api/v1/"; // local api server
            zomatoBaseUrl = "https://developers.zomato.com/api/v2.1/"; // zomato api
        }
    }




    public static class LoginProvider {
        public static final int FACEBOOK = 0;
        public static final int GOOGLE = 1;
    }
}
