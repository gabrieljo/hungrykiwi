package app.me.hungrykiwi.pages.restaurant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.pages.post.PostHolder;

/**
 * restraurant adapter
 * Created by user on 10/18/2016.
 */
public class RestrAdapter extends RecyclerView.Adapter<RestrHolder>{
    Context mContext;
    ArrayList<Restr> mList;

    public RestrAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
    }

    @Override
    public RestrHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.list_restaurant, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new RestrHolder(view, this.mContext);
    }

    public void more(Restr[] restrs) {
        int count = restrs.length;
        for(int i=0; i < count; i++) {
            this.mList.add(restrs[i]);
        }
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RestrHolder holder, int position) {
        Restr restr = this.mList.get(position);
        holder.set(restr);
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }
}
