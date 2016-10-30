package app.me.hungrykiwi.component.rate_comment_dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.Comment;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.service.RecipeService;
import app.me.hungrykiwi.service.RestrService;
import app.me.hungrykiwi.utils.Utility;

/**
 * rate comment adapter
 * Created by user on 10/26/2016.
 */
public class RateCommentAdapter extends RecyclerView.Adapter<RateCommentAdapter.RateCommentHolder>{

    ArrayList<Comment> mList;
    Context mContext;
    int mMode;
    int id;
    String url;

    public RateCommentAdapter(Context mContext, int mMode) {
        this.mContext = mContext;
        this.mMode = mMode;
        this.mList = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * request more data from server
     */
    public void more() {
        switch (this.mMode) {
            case RateCommentDialog.Mode.RECIPE_COMMENT:
                new RecipeService(this.mContext).readComment(this.id, this.url, this);
                break;
            case RateCommentDialog.Mode.RESTR_COMMENT:
                new RestrService(this.mContext).readComment(this.id, this.url, this);
            break;
        }
    }

    /**
     * read data from server
     */
    public void read(Comment[] comments, String url) {
        this.url = url;
        int count = comments.length;
        for(int i=0; i< count; i++) {
            this.mList.add(comments[i]);
        }
        this.notifyDataSetChanged();
    }

    /**
     * refresh comments
     */
    public void refresh() {
        this.mList.clear();
        this.url = null;
        this.more();
        this.notifyDataSetChanged();
    }

    /**
     * delete comment
     * @param position
     */
    public void delete(int position) {
        this.mList.remove(position);
        this.notifyDataSetChanged();
    }

    /**
     * edit comment data
     * @param position
     * @param newComment
     */
    public void edit(int position, Comment newComment) {
        this.mList.set(position, newComment);
        this.notifyDataSetChanged();
    }



    @Override
    public RateCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.list_rate_comment, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new RateCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(RateCommentHolder holder, int position) {
        if(position == this.getItemCount() -2 && this.url != null) this.more();
        Comment comment= this.mList.get(position);
        holder.set(comment, position);

    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    class RateCommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image; // user image
        TextView textName, textTime, textComment; // name and comment
        FrameLayout fmOption;
        RatingBar ratingBar;
        int position;
        int comment_id;
        public RateCommentHolder(View view) {
            super(view);
            this.image = (ImageView)view.findViewById(R.id.imageUser);
            this.textName = (TextView) view.findViewById(R.id.textName);
            this.textTime = (TextView) view.findViewById(R.id.textTime);
            this.textComment = (TextView)view.findViewById(R.id.textComment);
            this.fmOption = (FrameLayout) view.findViewById(R.id.fmOption);
            this.ratingBar = (RatingBar)view.findViewById(R.id.ratingb);

        }

        /**
         * set comment to view
         * @param comment
         * @param position
         */
        public void set(Comment comment, int position) {
            this.position = position;
            this.comment_id = comment.getId();

            User user = comment.getUser();
            if(user.getIsRestr() == 0 ) { // user comment
                HungryClient.userImage(mContext, this.image, user.getImgPath());
                this.textName.setText(user.getName());
            } else if (user.getIsRestr() == 1 ) { // restr comment
                Restr restr = user.getRestr();
                HungryClient.userImage(mContext, this.image, restr.getImg());
                this.textName.setText(restr.getName());
            }

            if(user.getId() == User.getAppUser().getId()){
                this.fmOption.setVisibility(View.VISIBLE);
                ImageView edit = (ImageView)this.fmOption.findViewById(R.id.edit);
                ImageView delete = (ImageView)this.fmOption.findViewById(R.id.delete);
                edit.setOnClickListener(this);
                delete.setOnClickListener(this);
                edit.setTag(comment.getComment());
            } else {
                this.fmOption.setVisibility(View.GONE);
            }
            this.textTime.setText(Utility.calcPastTime(comment.getCreatedAt()));
            this.textComment.setText(comment.getComment());
            this.ratingBar.setRating(comment.getRate());
        }
        /**
         * edit comment
         * @param content
         */
        public void editComment(String content) {
            // TODO : when user click edit button to edit comment
        }

        /**
         * confirm delete
         */
        public void deleteComment() {
            // TODO : request server to delete comment
//            new ViewUtil().confirmDialog(mContext, "Delete", "Would you like to delete?", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    new PostService(mContext).deleteComment(commentId, postId, postHolderId, CommentAdapter.this, postAdapter);
//                }
//            });
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit: // edit comment
                    this.editComment(String.valueOf(v.getTag()));
                    break;
                case R.id.delete: // delete comment
                    this.deleteComment();
                    break;
            }
        }
    }

}
