package app.me.hungrykiwi.http;

/**
 * configuration for connecting to database
 * Created by user on 10/3/2016.
 */
public class Config {
    public static String baseHome  = "";
    public static final boolean isAlive = false;

    static {
        if(isAlive == true) {
            // TODO : When connected with real server.
        } else {
            baseHome = "http://192.168.1.17:8000/"; // laravel local server
        }
    }




    public static class LoginProvider {
        public static final int FACEBOOK = 0;
        public static final int GOOGLE = 1;
    }
}
