package app.me.hungrykiwi.activities.recipe_detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.BaseActivity;
import app.me.hungrykiwi.component.rate_comment_dialog.RateCommentDialog;
import app.me.hungrykiwi.component.write_rate_comment_dialog.WriteRateCommentDialog;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.Recipe;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.service.RecipeService;

public class RecipeDetailActivity extends BaseActivity implements View.OnClickListener{
    Recipe recipe;
    RateCommentDialog commentDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // fetch recipe data
        this.initRecipe();
    }

    /**
     * read recipe from server
     */
    private void initRecipe() {
        int recipeId = this.getIntent().getIntExtra(getString(R.string.key_recipe_id), 0);
        new RecipeService(this).readById(recipeId);
    }

    /**
     * attach recipe to activity screen
     * @param recipe
     */
    public void read(Recipe recipe) {
        if(recipe != null) {
            this.recipe = recipe;
            View view = this.findViewById(R.id.collapse);
            HungryClient.recipeImage(this, (ImageView)view.findViewById(R.id.imageCollapse), recipe.getImg());
            this.setToolbar((Toolbar) this.findViewById(R.id.toolbar), recipe.getName(), true);
            ((TextView)this.findViewById(R.id.textRecipeName)).setText(recipe.getName());
            ((TextView)this.findViewById(R.id.textMinute)).setText(String.valueOf(recipe.getRequired_min()));
            ((TextView)this.findViewById(R.id.textIngre)).setText(recipe.getIngre());
            ((TextView)this.findViewById(R.id.textRecipeContent)).setText(recipe.getContent());
            ((TextView)this.findViewById(R.id.textViewNum)).setText(String.valueOf(recipe.getView()));
            ((TextView)this.findViewById(R.id.textRating)).setText(String.valueOf(recipe.getRating()));
            ((TextView)this.findViewById(R.id.textCommentNum)).setText(String.valueOf(recipe.getComment_num()));


            if(recipe.getIs_vegi() == 1) (this.findViewById(R.id.linearVegi)).setVisibility(View.GONE); // if vegi

            this.findViewById(R.id.lnRecipeComment).setOnClickListener(this);
            this.findViewById(R.id.lnWriteComment).setOnClickListener(this);
            if(recipe.getUser() != null) {
                User user = recipe.getUser();
                ((TextView)this.findViewById(R.id.textUserName)).setText(user.getName());
            }
        }
    }


    /**
     * show comment
     */
    public void comment(boolean show) {
        if(this.commentDialog == null) this.commentDialog = new RateCommentDialog(this, RateCommentDialog.Mode.RECIPE_COMMENT);

        if(this.recipe != null) {
            if(show == true) this.commentDialog.show(this.recipe.getId());

            else this.commentDialog.dismiss();
        }
    }

    /**
     * add menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * react to when menu item is selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_comment :
                this.comment(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnRecipeComment :
                this.comment(true);
                break;
            case R.id.lnWriteComment:
                new WriteRateCommentDialog(this, WriteRateCommentDialog.Mode.RECIPE_WRITE, this.recipe.getId()).show();
                break;
        }
    }

    /**
     * refresh comment
     */
    public void refreshComment() {
        if(this.commentDialog != null && this.commentDialog.isShowing() == true) {
            this.commentDialog.refresh();
        } else if (this.commentDialog != null && this.commentDialog.isShowing() == false) {
            this.commentDialog.refresh();
            this.commentDialog.show();
        } else if(this.commentDialog == null) {
            this.comment(true);
        }
    }
}
