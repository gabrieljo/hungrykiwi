package app.me.hungrykiwi.pages.recipe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.recipe_detail.RecipeDetailActivity;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.Recipe;
import app.me.hungrykiwi.service.RecipeService;
import app.me.hungrykiwi.utils.Utility;

/**
 * recipe holder
 * Created by user on 10/25/2016.
 */

public class RecipeHolder extends RecyclerView.ViewHolder{
    ImageView image;
    TextView textRate, textTitle, textViewNum;
    Context mContext;
    View root;

    public RecipeHolder(View view, Context mContext) {
        super(view);

        this.mContext = mContext;
        this.image = (ImageView)view.findViewById(R.id.imageRecipe);
        this.textRate = (TextView) view.findViewById(R.id.textRating);
        this.textTitle = (TextView) view.findViewById(R.id.textTitle);
        this.textViewNum = (TextView) view.findViewById(R.id.textViewNum);
        this.root = view;

        int width = new Utility().screenWidth(this.mContext) / 2;
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, width));

    }

    /**
     * set views for recipe
     * @param recipe
     */
    public void set(final Recipe recipe) {
        HungryClient.recipeImage(this.mContext, this.image, recipe.getImg());
        this.textRate.setText(String.valueOf(recipe.getRating()));
        this.textTitle.setText(String.valueOf(recipe.getName()));
        this.textViewNum.setText(String.valueOf(recipe.getView()));
        this.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // start activity and fetch data
                Intent intent = new Intent(mContext, RecipeDetailActivity.class)
                        .putExtra(mContext.getString(R.string.key_recipe_id), recipe.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
