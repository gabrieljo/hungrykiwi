package app.me.hungrykiwi.activities.upload_post;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.BaseActivity;
import app.me.hungrykiwi.pages.upload_post.UploadPostFragment;
import app.me.hungrykiwi.pages.upload_post_restr.UploadPostRestrFragment;
import app.me.hungrykiwi.utils.Utility;

/**
 * class for upload post or advertisement for restaurant
 */
public class PostUploadActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{

    // --- VIEWS --- ///
    TabLayout mTab;
    ViewPager mPager;

    UploadPostFragment mFragPost; // fragment for uploading post
    UploadPostRestrFragment mFragRestr; // fragment for uploading restraurant advertisement

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // -- TAB LAYOUT AND TOOLBAR ---///
        this.setToolbar((Toolbar)this.findViewById(R.id.toolbar), "New Post", true);
        this.mPager = (ViewPager)this.findViewById(R.id.pager);
        this.mTab = (TabLayout)this.findViewById(R.id.tablayout);
        this.mTab.addOnTabSelectedListener(this);
        this.mFragPost = new UploadPostFragment();
        this.mFragRestr = new UploadPostRestrFragment();
        this.setTabLayout(this.mTab,
                this.mPager,
                new Fragment[]{this.mFragPost, this.mFragRestr},
                new String[]{"Post", "Restaurant"});
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.mTab != null)
            this.mTab.removeOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_upload_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_save : // save post
                if(this.mFragPost.isReady() == true)  this.mFragPost.post();
                else new Utility().toast(this, "Type in post content first");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        this.mPager.setCurrentItem(tab.getPosition());
    }
}
