package app.me.hungrykiwi.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import app.me.hungrykiwi.activities.recipe_detail.RecipeDetailActivity;
import app.me.hungrykiwi.component.Progress;
import app.me.hungrykiwi.component.rate_comment_dialog.RateCommentAdapter;
import app.me.hungrykiwi.component.write_rate_comment_dialog.WriteRateCommentDialog;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.http.hungry_kiwi.HungryResponse;
import app.me.hungrykiwi.http.hungry_kiwi.HungryURL;
import app.me.hungrykiwi.model.Comment;
import app.me.hungrykiwi.model.Recipe;
import app.me.hungrykiwi.model.post.Post;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.pages.recipe.RecipeAdapter;
import cz.msebera.android.httpclient.Header;

/**
 * recipe service to connect with server
 * Created by user on 10/3/2016.
 */
public class RecipeService {
    Context mContext;

    public RecipeService(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * read data
     * @param url
     */
    public void read(String url, final RecipeAdapter adapter) {
        if(url == null) url = HungryURL.getPostPagination(HungryURL.Routing.recipe, 1);

        FileBaseDB db = new FileBaseDB();
        int min = db.getRecipeFilterMin(this.mContext);
        boolean vegi = db.getRecipeFilterVegi(this.mContext);
        int criterial = db.getRecipeFilterCriterial(this.mContext);

        HashMap<String, Object> coll = new HashMap<>();
        coll.put("require_min", min);
        coll.put("is_vegi", vegi);
        if(criterial  == RecipeAdapter.REFRESH_BY_RATE) coll.put("criterial", 0);
        else if(criterial  == RecipeAdapter.REFRESH_BY_VIEW) coll.put("criterial", 1);

        url = HungryURL.addBodyToGetUrl(url, false, coll);
        HungryClient client = new HungryClient();
        client.get(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String next = response.getString("next_page_url");
                    JSONArray jsonData = response.getJSONArray("data");
                    int count = jsonData.length();
                    Recipe[] recipes = new Recipe[count];
                    Gson gson = new Gson();
                    for(int i=0 ;i <count ; i++) {
                        recipes[i] = gson.fromJson(jsonData.getJSONObject(i).toString(), Recipe.class);
                    }
                    adapter.read(next, recipes);
                } catch(Exception ex) {
                    Log.d("INFO", "RecipeService error : "+ex.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    /**
     * show specific recipe data by id
     * @param recipe_id
     */
    public void readById(int recipe_id) {
        Progress.on(this.mContext);
        HashMap<String, Object> coll =new HashMap<>();

        coll.put("id", recipe_id);
        String url = HungryURL.getRelationalUrl(HungryURL.Routing.Type.RECIPE, null, recipe_id);
        HungryClient client = new HungryClient();
        client.get(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Progress.off();
                    Recipe recipe = new Gson().fromJson(response.getJSONObject("data").toString(), Recipe.class);
                    User user = new Gson().fromJson(response.getJSONObject("user").toString(), User.class);
                    user.setName(user.getfName() + " "+user.getlName());
                    recipe.setUser(user);
                    if (mContext instanceof RecipeDetailActivity) {
                        ((RecipeDetailActivity) mContext).read(recipe);
                    }
                }catch(Exception ex) {
                    Log.d("INFO", "RecipeService read error : "+ex.getMessage());
                }
            }
        });
    }

    /**
     * read comment
     * @param id recipe id
     * @param url url to request
     * @param adapter
     */
    public void readComment(int id, String url, final RateCommentAdapter adapter) {
        Log.d("INFO", "CALLED");
        if(url == null) url = HungryURL.getRelationalUrl(HungryURL.Routing.Type.RECIPE_COMMENT, null, id);
        HungryClient client = new HungryClient();
        client.get(url, new HungryResponse(this.mContext) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String next = response.getString("next_page_url");
                    JSONArray jsonData = response.getJSONArray("data");
                    int count = jsonData.length();
                    Comment[] comments = new Comment[count];
                    Gson gson = new Gson();
                    JSONObject jsonEach;
                    for(int i=0; i< count; i++) {
                        jsonEach = jsonData.getJSONObject(i);
                        comments[i] =  gson.fromJson(jsonEach.toString(), Comment.class);
                        User user = gson.fromJson(jsonEach.getJSONObject("user").toString(), User.class);
                        user.setName(user.getfName()+" "+user.getlName());
                        if(user.getIsRestr() == 1) user.setRestr(gson.fromJson(jsonEach.getJSONObject("restr").toString(), Restr.class));
                        comments[i].setUser(user);
                    }
                    adapter.read(comments, next);
                } catch(Exception ex) {
                    Log.d("INFO", "RecipeService : "+ex.getMessage());
                }
            }
        });
    }

    /**
     * store comment for recipe
     * @param comment
     * @param recipe_id
     */
    public void storeComment(String comment, int recipe_id, int rate) {
        try {
            Progress.on(this.mContext);
            String url = HungryURL.postRelationalUrl(HungryURL.Routing.Type.RECIPE_COMMENT, recipe_id);
            HungryClient client = new HungryClient();
            client.put("comment", comment);
            client.put("rate", rate);
            client.post(url, client.getParam(), new HungryResponse(this.mContext) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Progress.off();
                    if(mContext instanceof RecipeDetailActivity) ((RecipeDetailActivity)mContext).refreshComment();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Progress.off();
                }
            });
        }catch(Exception ex) {
            Log.d("INFO", "RecipeService storeComment : "+ex.getMessage());

        }

    }
}
