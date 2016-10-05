package app.me.hungrykiwi.pages;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * fragment adapter for sns, restaurant and recipe fragments
 * Created by peterlee on 2016-09-13.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter{
    ArrayList<Fragment> mFragmentList;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    /**
     * add fragments
     * @param frag
     */
    public void add(Fragment frag) {
        this.mFragmentList.add(frag);
    }
}
