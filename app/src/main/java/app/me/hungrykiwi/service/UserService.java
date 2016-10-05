package app.me.hungrykiwi.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.me.hungrykiwi.activities.login.LoginActivity;
import app.me.hungrykiwi.activities.main.MainActivity;
import app.me.hungrykiwi.http.HungryClient;
import app.me.hungrykiwi.http.HungryURL;
import app.me.hungrykiwi.model.User;
import app.me.hungrykiwi.utils.Utility;
import cz.msebera.android.httpclient.Header;

/**
 * service for user
 * Created by user on 10/3/2016.
 */

public class UserService {

    // application info
    Context mContext;

    // --- DOUBLE AUTOMAIC LOGIN PREVENTION --- //
    static boolean isAgain = false;

    public UserService(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * login process
     * @param email user email
     * @param fName user first-name
     * @param lName user last-name
     * @param imgPath user image url
     * @param imgCover user cover image url
     * @param provider facebook & google
     * @param token token provided from provider services
     */
    public void login(String email, String fName, String lName, String imgPath, String imgCover, int provider, String token) {
        if(UserService.isAgain == false) {
            UserService.isAgain = true;
            if (imgCover == null) imgCover = new String("NULL");
            if (imgPath == null) imgPath = new String("NULL");
            String url = new HungryURL().postUrl(HungryURL.Routing.userLogin);
            HungryClient client = new HungryClient();
            client.put("email", email);
            client.put("first_name", fName);
            client.put("last_name", lName);
            client.put("is_restr", 0);
            client.put("img", imgPath);

            client.put("cover_img", imgCover);
            client.put("provider", provider);
            client.put("token", token);

            client.post(url, client.getParam(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("INFO", "123");
                    try {
                        HungryClient.setToken(response.getString("token"));
                        Gson gson = new Gson();
                        User appUser = User.getAppUser();
                        appUser = gson.fromJson(response.getJSONObject("user").toString(), User.class);
                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                    } catch (JSONException ex) {
                        Log.d("INFO", "LOGIN JSON EXCEPTION : " + ex.getMessage());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Utility.goWeb(responseString, mContext);
                    isAgain = false;
                }
            });
        }
    }



}
