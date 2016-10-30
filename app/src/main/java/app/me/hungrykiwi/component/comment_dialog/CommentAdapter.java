package app.me.hungrykiwi.component.comment_dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.Comment;
import app.me.hungrykiwi.model.user.Restr;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.pages.post.PostAdapter;
import app.me.hungrykiwi.service.PostService;
import app.me.hungrykiwi.utils.Utility;

/**
 * comment adapter
 * Created by user on 10/14/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{
    Context mContext;
    ArrayList<Comment> mList;
    String next;
    int postId;
    int postHolderId;
    PostAdapter postAdapter;

    public CommentAdapter(Context mContext, int postHolderId, PostAdapter postAdapter) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        this.postHolderId = postHolderId;
        this.postAdapter = postAdapter;
    }


    /**
     * show comment by post id
     * @param postId
     */
    public void read(int postId) {
        new PostService(this.mContext).readComment(postId, this.next, this);
        this.postId = postId;
    }

    /**
     * read from already shown comment list
     */
    public void read() {
        new PostService(this.mContext).readComment(this.postId, this.next, this);
    }



    /**
     * firstly read data
     */
    public void refresh() {
        this.clear();
        this.notifyDataSetChanged();
        new PostService(this.mContext).readComment(this.postId, null, this);
    }
    /**
     * add more comment to adapter
     * @param comments
     * @param next
     */
    public void more(Comment[] comments, String next) {
        this.next = next;
        int count = comments.length;
        for(int i=0; i< count; i++) {
            this.mList.add(comments[i]);
        }
        this.notifyDataSetChanged();
    }



    /**
     * clear out all the comments
     */
    public void clear() {
        this.next = null;
        this.mList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.list_comment, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        // --- READ MORE COMMENT FROM SERVER --- ///
        if(position == this.getItemCount() - 1 && this.next != null) {
            this.read();
        }

        Comment comment = this.mList.get(position);
        holder.set(comment);

    }



    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    /**
     * comment holder for saving memory
     */
    class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image; // user image
        TextView textName, textTime, textComment; // name and comment
        FrameLayout fmOption;
        public CommentHolder(View view) {
            super(view);
            this.image = (ImageView)view.findViewById(R.id.imageUser);
            this.textName = (TextView) view.findViewById(R.id.textName);
            this.textTime = (TextView) view.findViewById(R.id.textTime);
            this.textComment = (TextView)view.findViewById(R.id.textComment);
            this.fmOption = (FrameLayout) view.findViewById(R.id.fmOption);

        }

        /**
         * set info for comment views
         * @param comment
         */
        public void set(Comment comment) {
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
                edit.setTag(R.id.key_content, comment.getComment());
                edit.setTag(R.id.key_comment_id, comment.getId());
                delete.setTag(R.id.key_comment_id, comment.getId());
                delete.setTag(R.id.key_post_id, comment.getPostId());
            } else {
                this.fmOption.setVisibility(View.GONE);
            }
            this.textTime.setText(Utility.calcPastTime(comment.getCreatedAt()));
            this.textComment.setText(comment.getComment());
        }

        /**
         * edit comment
         * @param content
         * @param commentId
         */
        public void editComment(String content, final int commentId) {
            final EditText edit = (EditText)LayoutInflater.from(mContext).inflate(R.layout.edittext, null);
            edit.setText(content);
            edit.setSelection(content.length());
            new ViewUtil().editDialog(mContext, "Comment Edit", content, edit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new PostService(mContext).editComment(edit.getText().toString(), commentId, CommentAdapter.this);
                }
            });
        }

        /**
         * confirm delete
         */
        public void deleteComment(final int commentId, final int postId) {
            new ViewUtil().confirmDialog(mContext, "Delete", "Would you like to delete?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new PostService(mContext).deleteComment(commentId, postId, postHolderId, CommentAdapter.this, postAdapter);
                }
            });
        }

        /**
         * click event
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image :
                    break;
                case R.id.textName :
                    break;
                case R.id.textComment :
                    break;
                case R.id.edit:
                    this.editComment((String)v.getTag(R.id.key_content), (int)v.getTag(R.id.key_comment_id));
                    break;
                case R.id.delete:
                    this.deleteComment((int)v.getTag(R.id.key_comment_id), (int)v.getTag(R.id.key_post_id));
                    break;
            }
        }
    }
}
