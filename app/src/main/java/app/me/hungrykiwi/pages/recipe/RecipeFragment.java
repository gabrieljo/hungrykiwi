package app.me.hungrykiwi.pages.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.upload_recipe.UploadRecipeActivity;
import app.me.hungrykiwi.component.FrameFabs;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.service.FileBaseDB;
import app.me.hungrykiwi.utils.ActionUtil;

/**
 * recipe fragment
 * Created by peterlee on 2016-09-13.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener{
    FrameFabs frameFabs;
    RecipeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initFabs(); // init fabs
        this.initRecylcer(); // init recycler
    }

    /**
     * init recycler
     */
    public void initRecylcer() {
        RecyclerView recycler = (RecyclerView)this.getView().findViewById(R.id.recycler);

        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        this.adapter = new RecipeAdapter(this.getContext());
        recycler.setAdapter(this.adapter);
    }

    /**
     * init fabs
     */
    public void initFabs() {
        this.frameFabs =  (FrameFabs)this.getView().findViewById(R.id.frameFabs);

        String[] titles = new String[] {
                "Create a new recipe",
                "Filter the search result",
                "Filter by rate",
                "Filter by view number",
        };

        int[] rids = new int[] {
                R.mipmap.ic_recipe,
                R.mipmap.ic_filter,
                R.mipmap.ic_rate,
                R.mipmap.ic_view,
        };

        View.OnClickListener[] listeners = new View.OnClickListener[] {
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // create a new recipe
                        startActivity(new Intent(getContext(), UploadRecipeActivity.class));
                        frameFabs.toggle();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // filter search result
                        new ViewUtil().recipefilterDialog(getContext(), adapter, "Filter", null);
                        frameFabs.toggle();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FileBaseDB().setRecipeFilterCriterial(getContext(), RecipeAdapter.REFRESH_BY_RATE);
                        adapter.refresh();
                        frameFabs.toggle();
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FileBaseDB().setRecipeFilterCriterial(getContext(), RecipeAdapter.REFRESH_BY_VIEW);
                        adapter.refresh();
                        frameFabs.toggle();
                    }
                },
        };
        FloatingActionButton fab = (FloatingActionButton)this.getView().findViewById(R.id.fabMain);
        fab.setOnClickListener(this);
        this.frameFabs.addFab(titles, rids, listeners, fab);
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
     * refresh
     */
    public void refresh() {
        if(this.adapter != null) {
            this.adapter.refresh();
        }
    }
}
