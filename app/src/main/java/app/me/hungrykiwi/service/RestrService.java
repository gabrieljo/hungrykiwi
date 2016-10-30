package app.me.hungrykiwi.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.me.hungrykiwi.component.rate_comment_dialog.RateCommentAdapter;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.http.hungry_kiwi.HungryResponse;
import app.me.hungrykiwi.http.hungry_kiwi.HungryURL;
import app.me.hungrykiwi.http.zomato.ZomatoClient;
import app.me.hungrykiwi.http.zomato.ZomatoURL;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.pages.restaurant.RestrAdapter;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 10/3/2016.
 */

public class RestrService {

    Context mContext;

    public RestrService(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * search restaurant by coordinate
     * @param latitude
     * @param longitude
     */
    public void search(double latitude, double longitude, float radius, final RestrAdapter adapter) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("lat", latitude);
        body.put("lon", longitude);
        body.put("radius", radius);
        String url = ZomatoURL.getUrl(ZomatoURL.Routing.SEARCH, body);
        ZomatoClient client = new ZomatoClient();
        client.get(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray jsonData = response.getJSONArray("restaurants");
                    int count = jsonData.length();
                    Restr[] restres = new Restr[count];
                    Gson gson = new Gson();
                    JSONObject jsonEach;

                    for (int i=0; i< count; i++) {
                        jsonEach = jsonData.getJSONObject(i).getJSONObject("restaurant");

                        restres[i] = gson.fromJson(jsonEach.toString(), Restr.class);
                        JSONObject jsonLocation = jsonEach.getJSONObject("location");
                        restres[i].setAddress(jsonLocation.getString("address"));
                        restres[i].setLocality(jsonLocation.getString("locality"));
                        restres[i].setCity(jsonLocation.getString("city"));
                        restres[i].setLat(jsonLocation.getDouble("latitude"));
                        restres[i].setLongitude(jsonLocation.getDouble("longitude"));

                        JSONObject jsonRating = jsonEach.getJSONObject("user_rating");
                        restres[i].setRating(Float.valueOf(jsonRating.getString("aggregate_rating")));
                        restres[i].setZomato_id(restres[i].getId());
                        restres[i].setId(0);
                    }

                    adapter.more(restres);

                } catch(JSONException ex ) {
                    Log.d("INFO", "RestrService 123 : "+ex.getMessage());
                } catch(Exception ex) {
                    Log.d("INFO", "RestrService 44 : "+ex.getMessage());
                }
            }
        });

    }

    /**
     * read comment for restaurant
     * @param id
     * @param url
     * @param rateCommentAdapter
     */
    public void readComment(int id, String url, RateCommentAdapter rateCommentAdapter) {
        if(url == null) url = HungryURL.getRelationalUrl(HungryURL.Routing.Type.RESTR_COMMENT, null, id);
        HungryClient client = new HungryClient();
        client.get(url, new HungryResponse(this.mContext) {

        });

    }


    /**
     * store comment for specific rstaurant
     * @param comment
     * @param id
     */
    public void storeComment(String comment, int id) {
        String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.RESTR_COMMENT, id);
        HungryClient client = new HungryClient();
        client.post(url, new HungryResponse(this.mContext) {

        });
    }
}
