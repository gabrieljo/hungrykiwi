package app.me.hungrykiwi.pages.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.me.hungrykiwi.R;

/**
 * Created by peterlee on 2016-09-13.
 */
public class RestaurantFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, null);
        return view;
    }
}
