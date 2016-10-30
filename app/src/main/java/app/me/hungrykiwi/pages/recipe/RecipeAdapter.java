package app.me.hungrykiwi.pages.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.model.Recipe;
import app.me.hungrykiwi.service.RecipeService;

/**
 * recipe adapter
 * Created by user on 10/25/2016.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{
    Context mContext;
    ArrayList<Recipe> mList;
    String url;
    public static final int REFRESH_BY_RATE = 2;
    public static final int REFRESH_BY_VIEW = 3;

    public RecipeAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        new RecipeService(this.mContext).read(null, this);
    }

    /**
     * read
     * @param recipes
     */
    public void read(String next, Recipe[] recipes) {
        this.url = next;
        int count = recipes.length;
        for(int i=0; i< count ; i++) {
            this.mList.add(recipes[i]);
        }
        this.notifyDataSetChanged();
    }

    /**
     * request more recipe data
     */
    public void more() {
        new RecipeService(this.mContext).read(url, this);
    }


    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeHolder(LayoutInflater.from(this.mContext).inflate(R.layout.list_recipe, null), this.mContext);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        if(position == this.getItemCount() - 2) this.more();
        Recipe recipe = this.mList.get(position);
        holder.set(recipe);
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    /**
     * refresh
     */
    public void refresh() {
        this.url = null;
        this.mList.clear();
        this.notifyDataSetChanged();
        this.more();
    }
}
