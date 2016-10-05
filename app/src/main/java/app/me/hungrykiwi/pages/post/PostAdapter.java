package app.me.hungrykiwi.pages.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.me.hungrykiwi.model.post.SNS;

/**
 * Created by peterlee on 2016-09-13.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.SNSHolder>{
    ArrayList<SNS> mSnsList;
    Context mContext;

    public PostAdapter(Context mContext) {
        this.mContext = mContext;
        this.mSnsList = new ArrayList<>();
    }

    @Override
    public SNSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SNSHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SNSHolder extends RecyclerView.ViewHolder{

        public SNSHolder(View itemView) {
            super(itemView);
        }
    }
}
