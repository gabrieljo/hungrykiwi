package app.me.hungrykiwi.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;

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
     * add new body info
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        if(value instanceof File){
            try {
                this.params.put(key, (File) value); // add after upcasting to file object
            } catch(FileNotFoundException ex) {
                Log.d("INFO", "HungryClient error 1 : "+ex.getMessage());
            }
        } else {
            params.put(key, value);    // add
        }
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
        return info;
    }
}
