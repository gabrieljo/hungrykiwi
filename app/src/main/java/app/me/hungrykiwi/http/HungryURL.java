package app.me.hungrykiwi.http;

import java.util.HashMap;
import java.util.Map;

/**
 * get url address
 * Created by user on 10/3/2016.
 */

public class HungryURL {
    /**
     * url for get request method
     * @param url
     * @param route
     * @return
     */
    public String getUrl(String url, HashMap<String, String> route) {
        StringBuilder builder = new StringBuilder(Config.baseHome + url);
        int count = 0;
        for(Map.Entry<String, String> each : route.entrySet()) {
            if(count == 0) {
                builder.append("?");
                count = 1;
            }else {
                builder.append("&");
            }
            builder.append(each.getKey() + "=" + each.getValue());
        }
        return builder.toString();
    }

    /**
     * url for post request method
     * @param url
     * @return
     */
    public String postUrl(String url) {
        return Config.baseHome + url;
    }

    public static class Routing {
        public static final String userLogin = "login/";
    }
}
