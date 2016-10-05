package app.me.hungrykiwi.activities;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.pages.FragmentAdapter;

/**
 * Created by peterlee on 2016-09-13.
 */
public class BaseActivity extends AppCompatActivity{


    public void setToolbar(Toolbar toolbar, String title, boolean backArrow) {
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(title);
        if(backArrow == true) {
            ActionBar actionBar = this.getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) this.finish();
        return super.onOptionsItemSelected(item);
    }


    /**
     * set tab layout
     * @param tabLayout
     * @param pager
     * @param fragments
     * @param titles
     */
    public void setTabLayout(TabLayout tabLayout, ViewPager pager, Fragment[] fragments, String[] titles) {
        if(tabLayout != null) {
            int count = titles.length;
            FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
            try {
                for (int i = 0; i < count; i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
                    adapter.add(fragments[i]);
                }
            } catch(Exception ex) {}
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            pager.setAdapter(adapter);
        }
    }

    /**
     * set drawerlayout
     * @param drawer drawerlayout
     * @param toolbar toolbar
     * @param nav navigationview
     * @param title title for toolbar
     */
    public ActionBarDrawerToggle setDrawerNavi(DrawerLayout drawer, Toolbar toolbar, NavigationView nav, String title) {
        this.setToolbar(toolbar, title, false); // set default toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close); // set togglelistener
        toggle.syncState(); // validate
        drawer.addDrawerListener(toggle); // set listener
        return toggle;
    }
}
