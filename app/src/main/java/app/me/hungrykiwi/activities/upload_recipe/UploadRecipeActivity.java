package app.me.hungrykiwi.activities.upload_recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.BaseActivity;

/**
 * upload recipe
 */
public class UploadRecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        // init toolbar
        this.setToolbar((Toolbar)this.findViewById(R.id.toolbar), "New Recipe", true);
    }
}
