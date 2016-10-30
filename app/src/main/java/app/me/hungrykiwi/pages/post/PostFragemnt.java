package app.me.hungrykiwi.pages.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.upload_post.PostUploadActivity;
import app.me.hungrykiwi.activities.upload_recipe.UploadRecipeActivity;
import app.me.hungrykiwi.component.FrameFabs;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.model.post.Post;
import app.me.hungrykiwi.pages.recipe.RecipeAdapter;
import app.me.hungrykiwi.service.FileBaseDB;

/**
 * Created by peterlee on 2016-09-13.
 * post fragment
 */
public class PostFragemnt extends Fragment implements View.OnClickListener{

    PostAdapter adapter;
    FloatingActionButton fabMain;
    FrameFabs frameFabs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, null);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // init views
        this.initViews();

        // init fabs
        this.initFabs();

    }

    public void initViews() {

        RecyclerView recycler = (RecyclerView) getView().findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.adapter = new PostAdapter(this.getContext(), recycler, this);
        recycler.setAdapter(this.adapter);



    }

    /**
     * init views
     */
    public void initFabs() {
        this.frameFabs =  (FrameFabs)this.getView().findViewById(R.id.frameFabs);

        String[] titles = new String[] {
                "Create a post with camera",
                "Create a post with gallery images",
                "Create a post",
        };

        int[] rids = new int[] {
                R.mipmap.ic_camera,
                R.mipmap.ic_gallery,
                R.mipmap.ic_post,
        };

        View.OnClickListener[] listeners = new View.OnClickListener[] {

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // filter search result
                        getContext().startActivity(new Intent(getContext(), PostUploadActivity.class).setType("camera"));
                        frameFabs.toggle();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(), PostUploadActivity.class).setType("gallery"));
                        frameFabs.toggle();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // create a post
                        getContext().startActivity(new Intent(getContext(), PostUploadActivity.class));
                        frameFabs.toggle();
                    }
                },
        };
        FloatingActionButton fab = (FloatingActionButton)this.getView().findViewById(R.id.fabMain);
        fab.setOnClickListener(this);
        this.frameFabs.addFab(titles, rids, listeners, fab);

    }

    /**
     * refresh adapter
     */
    public void refresh() {
        this.adapter.refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabMain :
                this.frameFabs.toggle();
                break;
        }
    }

    /**
     * edit post
     */
    public void goEdit(Post post) {
        Intent intent = new Intent(this.getContext(), PostUploadActivity.class);

        intent.putExtra("post_id", post.getId());
        intent.putExtra("content", post.getContent());
        intent.setType("edit");
        this.startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        this.refresh();
    }
}
