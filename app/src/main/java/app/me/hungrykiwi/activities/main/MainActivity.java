package app.me.hungrykiwi.activities.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import app.me.hungrykiwi.component.viewutil.PagerTransformer;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.pages.recipe.RecipeFragment;
import app.me.hungrykiwi.pages.restaurant.RestrFragment;
import app.me.hungrykiwi.pages.post.PostFragemnt;
import app.me.hungrykiwi.service.UserService;

/**
 * main activity for sns, restaurant and recipe
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener{
    // --- TAB LAYOUT AND SWIPE LAYOUT--- //
    ViewPager mPager;
    TabLayout mTab;

    // --- DRAWER LAYOUT AND TOOLBAR --- //
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;
    PostFragemnt mFragPost;
    RestrFragment mFragRestr;
    RecipeFragment mFragRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initViews();
    }


    /**
     * init views
     */
    public void initViews() {
        // toolbar with title
        this.mDrawer = (DrawerLayout)this.findViewById(R.id.drawer);
        NavigationView nav = (NavigationView)this.findViewById(R.id.navi);
        this.mToggle = this.setDrawerNavi(this.mDrawer, (Toolbar)this.findViewById(R.id.toolbar), nav, getString(R.string.app_name));
        nav.setNavigationItemSelectedListener(this);


        this.mPager = (ViewPager)this.findViewById(R.id.pager);
        this.mPager.setPageTransformer(true, new PagerTransformer());
        this.mTab = (TabLayout)this.findViewById(R.id.tablayout);
        this.mTab.addOnTabSelectedListener(this);
        this.mFragPost = new PostFragemnt();
        this.mFragRestr = new RestrFragment();
        this.mFragRecipe = new RecipeFragment();
        this.setTabLayout(this.mTab,
                this.mPager,
                new Fragment[]{this.mFragPost, this.mFragRestr, this.mFragRecipe},
                new String[]{"SNS", "Restaurant", "Recipe"});

        // header view
        View header = nav.getHeaderView(0);
        ImageView imageUser = (ImageView)header.findViewById(R.id.imageUser);
        TextView textName = (TextView)header.findViewById(R.id.textName);
        User user = User.getAppUser();
        textName.setText(user.getName());
        HungryClient.userImage(this, imageUser, user.getImgPath());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(this.mTab != null)
            this.mTab.addOnTabSelectedListener(this);
        if(this.mDrawer != null)
            this.mDrawer.addDrawerListener(this.mToggle);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.mTab != null)
            this.mTab.removeOnTabSelectedListener(this);
        if(this.mDrawer != null)
            this.mDrawer.removeDrawerListener(this.mToggle);
    }



    /**
     * when tab is selected..
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        this.mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void startLogout() {
        new ViewUtil().confirmDialog(this, "Logout", "Would you like to logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK, new Intent().putExtra("logout", true));
                finish();
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.favorite :
                Log.d("INFO", "1");
                break;
            case R.id.setting :
                Log.d("INFO", "2");
                break;
            case R.id.logout :
                this.startLogout();
                break;
        }
        return false;
    }



    /**
     * refresh data
     */
    public void refresh() {
        // TODO : select a currently visible fragment in order to decide what kind of data should be fetched.
        switch(this.mPager.getCurrentItem()) {
            case 0:
                if(this.mFragPost != null) this.mFragPost.refresh();
                break;
            case 1:
                if(this.mFragRestr != null) this.mFragRestr.refresh();
                break;
            case 2:
                if(this.mFragRecipe != null) this.mFragRecipe.refresh();
                break;
        }
    }


    /**
     * add menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.menu_refresh :
                this.refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserService.isAgain = false;
    }
}
