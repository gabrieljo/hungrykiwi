package app.me.hungrykiwi.activities.main;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.BaseActivity;
import app.me.hungrykiwi.pages.recipe.RecipeFragment;
import app.me.hungrykiwi.pages.restaurant.RestaurantFragment;
import app.me.hungrykiwi.pages.post.PostFragemnt;

/**
 * main activity for sns, restaurant and recipe
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener{
    // --- TAB LAYOUT AND SWIPE LAYOUT--- //
    ViewPager mPager;
    TabLayout mTab;
    SwipeRefreshLayout refresh;

    // --- DRAWER LAYOUT AND TOOLBAR --- //
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar with title
        this.mDrawer = (DrawerLayout)this.findViewById(R.id.drawer);
        NavigationView nav = (NavigationView)this.findViewById(R.id.navi);
        this.mToggle = this.setDrawerNavi(this.mDrawer, (Toolbar)this.findViewById(R.id.toolbar), nav, getString(R.string.app_name));
        nav.setNavigationItemSelectedListener(this);

        // set tablayout with title and fragmtns
        this.refresh = (SwipeRefreshLayout)this.findViewById(R.id.refresh);
        this.refresh.setOnRefreshListener(this);
        this.mPager = (ViewPager)this.findViewById(R.id.pager);
        this.mTab = (TabLayout)this.findViewById(R.id.tablayout);
        this.mTab.addOnTabSelectedListener(this);
        this.setTabLayout(this.mTab,
                this.mPager,
                new Fragment[]{new PostFragemnt(), new RestaurantFragment(), new RecipeFragment()},
                new String[]{"SNS", "Restaurant", "Recipe"});
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.drawer1 :
                Log.d("INFO", "1");
                break;
            case R.id.drawer2 :
                Log.d("INFO", "2");
                break;
            case R.id.drawer3 :
                Log.d("INFO", "3");
                break;
        }
        return false;
    }

    /**
     * when refreshed to refresh new data
     */
    @Override
    public void onRefresh() {
        // TODO : select a currently visible fragment in order to decide what kind of data should be fetched.
    }
}
