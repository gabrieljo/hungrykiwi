package app.me.hungrykiwi.http.hungry_kiwi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import app.me.hungrykiwi.http.Config;

import static android.R.attr.id;

/**
 * get url address
 * Created by user on 10/3/2016.
 */

public class HungryURL {
    /**
     * url for get request method
     * @param routing
     * @param route
     * @return
     */
    public static String getUrl(String routing, HashMap<String, Object> route) {
        StringBuilder builder = new StringBuilder(Config.baseUrl + routing);
        int count = 0;
        for(Map.Entry<String, Object> each : route.entrySet()) {
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

    public static String addBodyToGetUrl(String url, boolean isFirst, HashMap<String, Object> coll) {
        if(url != null) {
            StringBuilder builder = new StringBuilder(url);
            int count = 0;
            if(isFirst == false) count = 1;
            for(HashMap.Entry<String, Object> each : coll.entrySet()) {
                if(count == 0) {
                    builder.append("?");
                    count += 1;
                } else builder.append("&");
                builder.append(each.getKey()+ "=" +each.getValue());
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * url for post request method
     * @param routing
     * @return
     */
    public static String postUrl(String routing) {
        return Config.baseUrl + routing;
    }

    /**
     * get post pagination url
     * @param current
     * @return
     */
    public static String getPostPagination(String routing, int current) {
        StringBuilder builder = new StringBuilder(Config.baseUrl + routing);
        builder.append("?page="+current);
        builder.append("&limit="+Config.pageLimit);
        return builder.toString();
    }

    public static String postRelationalUrl(Routing.Type type, int... id) {
        String result = null;
        switch(type ){
            case POST_COMMENT:
                result = "post/"+id[0]+"/comment/";
                break;
            case POST_COMMENT_DELETE:
                result = "post/"+id[0]+"/comment/"+id[1];
                break;
            case POST_COMMENT_EDIT:
                result = "post/"+id[0]+"/comment/"+id[1]+"/edit/";
                break;
            case POST_LIKE:
                result = "post/"+id[0]+"/like/";
                break;
            case RESTR_COMMENT:
                result = "restr/"+id[0]+"/comment/";
                break;
            case RECIPE_COMMENT:
                result = "recipe/"+id[0]+"/comment/";
                break;
            case POST:
                result = "post/"+id[0]+"/";
                break;
            case POST_EDIT:
                result = "post/"+id[0]+"/edit/";
                break;
        }
        return Config.baseUrl + result;
    }

    public static String getRelationalUrl(Routing.Type type, HashMap<String, Object> coll, int... id) {
        StringBuilder result = new StringBuilder(Config.baseUrl);
        switch(type ){
            case POST_COMMENT:
                result.append("post/"+id[0]+"/comment/");
                break;
            case POST_COMMENT_DELETE:
                result.append("post/"+id[0]+"/comment/"+id[1]);
                break;
            case POST_COMMENT_EDIT:
                result.append("post/"+id[0]+"/comment/"+id[1]+"/edit/");
                break;
            case POST_LIKE:
                result.append("post/"+id[0]+"/like/");
                break;
            case RESTR_COMMENT:
                result.append("restr/"+id[0]+"/comment/");
                break;
            case RECIPE_COMMENT:
                result.append("recipe/"+id[0]+"/comment/");
                break;
            case POST:
                result.append("post/"+id[0]+"/");
                break;
            case POST_EDIT:
                result.append("post/"+id[0]+"/edit/");
                break;
            case RECIPE :
                result.append("recipe/"+id[0]);
                break;

        }
        int count = 0;
        if(coll != null) {
            for (Map.Entry<String, Object> each : coll.entrySet()) {
                if (count == 0) {
                    result.append("?");
                    count = 1;
                } else {
                    result.append("&");
                }
                result.append(each.getKey() + "=" + each.getValue());
            }
        }
        return result.toString();
    }

    /**
     * routing url info
     */
    public static class Routing {
        public static final String userLogin = "login/";
        public static final String post = "post/";
        public static final String restr = "restr/";
        public static final String recipe = "recipe";

        public enum Type {
            POST,
            POST_EDIT,
            POST_COMMENT,
            POST_COMMENT_DELETE,
            POST_COMMENT_EDIT,
            POST_LIKE,
            RESTR_COMMENT,
            RECIPE_COMMENT,
            RECIPE,
        }

    }

}
