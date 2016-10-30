package app.me.hungrykiwi.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import app.me.hungrykiwi.activities.login.LoginActivity;
import app.me.hungrykiwi.activities.main.MainActivity;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.http.hungry_kiwi.HungryResponse;
import app.me.hungrykiwi.http.hungry_kiwi.HungryURL;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.component.Progress;
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
    public static boolean isAgain = false;

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
        try {
            if (UserService.isAgain == false) {
                UserService.isAgain = true;
                Progress.on(this.mContext);
                if (imgCover == null) imgCover = new String("NULL");
                if (imgPath == null) imgPath = new String("NULL");
                String url = HungryURL.postUrl(HungryURL.Routing.userLogin);
                HungryClient client = new HungryClient();
                client.put("email", email);
                client.put("first_name", fName);
                client.put("last_name", lName);
                client.put("is_restr", 0);
                client.put("img", imgPath);
                client.put("cover_img", imgCover);
                client.put("provider", provider);
                client.put("token", token);
                client.post(url, client.getParam(), new HungryResponse(this.mContext) {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Progress.off();
                        try {
                            String token = response.getString("token");
                            if(token != null && token.equals("null") == false) {
                                HungryClient.setToken(token);
                                User user = new Gson().fromJson(response.getJSONObject("user").toString(), User.class);
                                user.setName(user.getfName() + " " + user.getlName());
                                User.setAppUser(user);
                                if(mContext instanceof LoginActivity) {
                                    LoginActivity activity = (LoginActivity) mContext;
                                    activity.startActivityForResult(new Intent(mContext, MainActivity.class), activity.RC_LOGOUT);
                                }
                            }
                        } catch (Exception ex) {
                            Log.d("INFO", "LOGIN JSON EXCEPTION : " + ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Progress.off();
                        isAgain = false;
                    }
                });
            }
        }catch(Exception ex) {
            Log.d("INFO", "UserService login : "+ex.getMessage());
        }
    }
}