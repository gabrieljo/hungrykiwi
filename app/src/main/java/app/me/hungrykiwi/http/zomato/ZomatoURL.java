package app.me.hungrykiwi.http.zomato;

import java.util.HashMap;
import java.util.HashSet;

import app.me.hungrykiwi.http.Config;

/**
 * Created by user on 10/18/2016.
 */

public class ZomatoURL {
    public static String getUrl(String routing, HashMap<String, Object> body) {
        StringBuilder builder = new StringBuilder(Config.zomatoBaseUrl + routing);
        int count = 0;
        for(HashMap.Entry<String, Object> each : body.entrySet()) {
            if(count == 0) {
                builder.append("?");
                count = 1;
            } else {
                builder.append("&");
            }
            builder.append(each.getKey()+"="+each.getValue());

        }
        return builder.toString();

    }

    public static class Routing {
        public static final String SEARCH = "search";
    }
}
