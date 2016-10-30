package app.me.hungrykiwi.http.hungry_kiwi;

import android.content.Context;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.viewutil.CircleTransform;

/**
 * http client to connect with server
 * Created by user on 10/3/2016.
 */

public class HungryClient extends AsyncHttpClient{
    private RequestParams params; // http request body info
    private static String token = null;
    public static void setToken(String token) {
        HungryClient.token = token;
    }

    public HungryClient() {
        this.params = new RequestParams();
        if(HungryClient.token != null)
            this.addHeader("Authorization", HungryClient.token);
    }

    /**
     * add new file to server
     * @param key
     * @param file
     */
    public void put(String key, File file) throws FileNotFoundException{
        this.params.put(key, file); // add after upcasting to file object
    }
    /**
     * add new body info
     * @param key
     * @param value
     */
    public void put(String key, Object value) throws Exception{
        this.params.put(key, value); // add after upcasting to file object
    }

    /**
     * add multiple images
     * @param key
     * @param files
     * @throws Exception
     */
    public void put(String key, File[] files) throws FileNotFoundException{
        this.params.put(key, files);
    }

    public RequestParams getParam() {
        return this.params;
    }


    @Override
    public String toString() {
        String info = null;
        if(this.params !=null) {
            info = this.params.toString();
        }
        return info
                +"\nTOKEN : "+token;
    }

    /**
     * load image
     * @param context
     * @param image
     * @param url
     */
    public static void image(Context context, ImageView image, String url) {
        Picasso.with(context).load(url).into(image);
    }

    public static void userImage(Context context, ImageView image, String url) {
        Picasso.with(context).load(url).transform(new CircleTransform()).placeholder(R.mipmap.sns_anonymous).error(R.mipmap.sns_anonymous).into(image);
    }

    public static void restrImage(Context context, ImageView image, String url) {
        Picasso.with(context).load(url).placeholder(R.mipmap.sns_restr).error(R.mipmap.sns_restr).into(image);
    }

    public static void recipeImage(Context context, ImageView image, String url) {
        Picasso.with(context).load(url).placeholder(R.mipmap.image_not_ready).error(R.mipmap.image_not_ready).into(image);
    }
}
