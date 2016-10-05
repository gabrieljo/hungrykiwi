package app.me.hungrykiwi.pages.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.me.hungrykiwi.R;

/**
 * Created by peterlee on 2016-09-13.
 */
public class PostFragemnt extends Fragment {


    PostAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sns, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recycler = (RecyclerView)getView().findViewById(R.id.recycler);
        this.adapter = new PostAdapter(this.getContext());
        recycler.setAdapter(this.adapter);
    }
}
