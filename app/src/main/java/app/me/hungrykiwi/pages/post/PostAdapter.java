package app.me.hungrykiwi.pages.post;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.model.post.Post;
import app.me.hungrykiwi.service.PostService;

/**
 * post adapter for sns
 * Created by peterlee on 2016-09-13.
 */
public class PostAdapter extends RecyclerView.Adapter<PostHolder>{
    ArrayList<Post> mPostList;
    Context mContext;
    String nextUrl;
    RecyclerView recyclerView;
    Fragment fragment;

    public PostAdapter(Context mContext) {
        this.mContext = mContext;
        this.mPostList = new ArrayList<>();
        this.read();
    }

    public PostAdapter(Context mContext, RecyclerView recyclerView, Fragment fragment) {
        this(mContext);
        this.recyclerView = recyclerView;
        this.fragment = fragment;
    }


    /**
     * set like
     * @param position
     * @param like
     */
    public void setLike(int position, boolean like) {
        Post post = this.mPostList.get(position);
        if(like == true) {
            post.setLikeNum(post.getLikeNum()+1);
        } else {
            post.setLikeNum(post.getLikeNum()-1);
        }
        this.mPostList.set(position, post);
        this.notifyDataSetChanged();
    }

    /**
     * set comment
     * @param position
     * @param comment
     */
    public void setComment(int position, boolean comment) {
        Post post = this.mPostList.get(position);
        if(comment == true) {
            post.setCommentNum(post.getCommentNum()+1);
        } else {
            post.setCommentNum(post.getCommentNum()-1);
        }
        this.mPostList.set(position, post);
        this.notifyDataSetChanged();
    }

    /**
     * start to get first page of post
     */
    public void read() {

        new PostService(this.mContext).read(this.nextUrl, this);
    }

    /**
     * add post from server
     * @param posts
     * @param nextUrl
     */
    public void add(Post[] posts, String nextUrl) {
        this.nextUrl = nextUrl;
        int count = posts.length;
        for(int i =0 ; i < count ; i ++) {
            this.mPostList.add(posts[i]);

        }
        this.notifyDataSetChanged();
    }

    /**
     * create view holder for item and save resource like memory
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.list_post, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new PostHolder(view, this.mContext, this, this.fragment);
    }

    /**
     * show items
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        try {
            if (position == this.getItemCount() - 2 && this.nextUrl != null) {
                this.read();
            }
            Post post = this.mPostList.get(position); // each post item
            holder.setView(post);
        } catch(Exception ex) {
            Log.d("INFO", "POST EXCEPTION : "+ex.getMessage());
        }
    }

    /**
     * items cound
     * @return
     */
    @Override
    public int getItemCount() {
        return this.mPostList.size();
    }

    /**
     * refresh post items
     */
    public void refresh() {
        this.nextUrl = null;
        this.mPostList.clear();
        this.notifyDataSetChanged();
        this.read();
    }

}
