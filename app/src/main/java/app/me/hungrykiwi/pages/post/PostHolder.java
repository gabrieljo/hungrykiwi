package app.me.hungrykiwi.pages.post;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.comment_dialog.CommentDialog;
import app.me.hungrykiwi.component.post_like_dialog.LikeDialog;
import app.me.hungrykiwi.component.share_sns_dialog.ShareSNSDialog;
import app.me.hungrykiwi.http.hungry_kiwi.HungryClient;
import app.me.hungrykiwi.model.post.Post;
import app.me.hungrykiwi.model.user.User;
import app.me.hungrykiwi.component.attached_image.ImageAdapter;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.service.PostService;
import app.me.hungrykiwi.utils.Utility;

/**
 * view holder for post
 * Created by user on 10/10/2016.
 */
public class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    PostAdapter adapter;
    ImageView imgUser; // image for user
    TextView textName, textTime, textContent, textLike, textComment;
    Context mContext;
    LinearLayout root;
    CommentDialog commentDialog;
    LikeDialog likeDialog;
    View view;
    Fragment mFrag;
    public PostHolder(View view, Context context, PostAdapter adapter, Fragment mFrag) {
        super(view);
        this.mFrag = mFrag;
        this.adapter = adapter;
        this.view = view;
        this.mContext = context;
        this.root = (LinearLayout)view.findViewById(R.id.linearRoot);
        this.imgUser = (ImageView)view.findViewById(R.id.imgUser);
        this.textName = (TextView)view.findViewById(R.id.textName);
        this.textTime = (TextView)view.findViewById(R.id.textTime);
        this.textContent = (TextView)view.findViewById(R.id.textContent);
        this.textLike = (TextView)view.findViewById(R.id.textLike);
        this.textComment = (TextView)view.findViewById(R.id.textComment);
        this.setClick(view); // set clicklistener
    }

    /**
     * set click listener for views
     */
    public void setClick(View view) {
        view.findViewById(R.id.lnLike).setOnClickListener(this);
        view.findViewById(R.id.lnComment).setOnClickListener(this);

    }

    /**
     * set info fetched from server
     * @param post
     * @throws Exception
     */
    public void setView(Post post) throws Exception{
        User user = post.getUser();
        HungryClient.userImage(this.mContext, this.imgUser, user.getImgPath());

        this.textName.setText(user.getName());
        this.textTime.setText(Utility.calcPastTime(post.getCreatedAt()));
        this.textContent.setText(post.getContent());
        this.textLike.setText(String.valueOf(post.getLikeNum()));
        this.textComment.setText(String.valueOf(post.getCommentNum()));

        RelativeLayout comment = (RelativeLayout)this.view.findViewById(R.id.rlComment);
        RelativeLayout share = (RelativeLayout)this.view.findViewById(R.id.rlShare);
        RelativeLayout like = (RelativeLayout)this.view.findViewById(R.id.rlLike);
        LinearLayout lnComment = (LinearLayout) this.view.findViewById(R.id.lnComment);
        LinearLayout lnLike = (LinearLayout) this.view.findViewById(R.id.lnLike);

        comment.setOnClickListener(this);
        share.setOnClickListener(this);
        like.setOnClickListener(this);
        lnLike.setOnClickListener(this);
        lnComment.setOnClickListener(this);

        share.setTag(post);
        comment.setTag(post.getId());
        like.setTag(post.getId());
        lnComment.setTag(post.getId());
        lnLike.setTag(post.getId());


        String[] imgs = post.getImgs();
        this.clearout(); // clear data like image or restaunrat info from re-used post
        if(imgs != null && imgs.length != 0) { // when post contains image info
            this.setImage(imgs);
        }
        if(user.getIsRestr() != 0) {
            // TODO Restaurant advertisement16dp
        }
        ImageView option = (ImageView)this.view.findViewById(R.id.imgOption);
        if(post.getUserId() == User.getAppUser().getId()) { // if app user is uploader of this post
            option.setVisibility(View.VISIBLE);
            option.setTag(R.id.key_post, post);
            option.setOnClickListener(this);
        } else {
            option.setVisibility(View.GONE);
        }
    }

    /**
     * clear image and restaurant information used before as post has re-used.
     */
    public void clearout() {
        if(this.root.getTag(R.string.key_attached_images) != null) {
            this.root.removeView((View)root.getTag(R.string.key_attached_images));
        }
    }


    /**
     * set imags
     * @param imgs
     */
    public void setImage(String[] imgs) {
        ViewUtil util = new ViewUtil();
        RecyclerView recyclerView = util.getImageRecycler(this.mContext, imgs, ImageAdapter.Mode.ATTACHED_IMAGE);
        util.setImageRecyclerGridLayout(recyclerView, imgs.length);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        this.root.addView(recyclerView, 2, params);
        this.root.setTag(R.string.key_attached_images, recyclerView);
    }

    public void toggleComment(boolean show, int postId) {
        if(show == true ) {
            this.commentDialog = new CommentDialog(this.mContext, this.adapter, this.getAdapterPosition());
            this.commentDialog.show(postId);
        } else {
            this.commentDialog.dismiss();
            this.commentDialog = null;
        }
    }

    public void toggleLike(boolean show, int postId) {
        if(show == true ) {
            this.likeDialog = new LikeDialog(this.mContext);
            this.likeDialog.show(postId);
        } else {
            this.likeDialog.dismiss();
            this.likeDialog = null;
        }
    }

    /**
     * app user change the content of post if post uploader is app user
     */
    public void postOption(final Post post) {
        final ViewUtil util = new ViewUtil();
        new ViewUtil().twoChice(this.mContext, "Post", "Please choose one of folloing options", "Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mFrag instanceof PostFragemnt) {
                    ((PostFragemnt)mFrag).goEdit(post);
                }
            }
        }, "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                util.confirmDialog(mContext, "Post Delete", "Are you sure to delete?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new PostService(mContext).delete(post.getId(), adapter);
                    }
                });
            }
        });
    }


    /**
     * click event handler
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlLike: // like click
                new PostService(this.mContext).like((int)v.getTag(), getAdapterPosition(), adapter);
                break;
            case R.id.rlComment: // comment click
                this.toggleComment(true, (int)v.getTag());
                break;
            case R.id.rlShare: // share click
                if(v.getTag() instanceof Post) {
                    Post post = (Post) v.getTag();
                    new ShareSNSDialog(this.mContext, mFrag).show(null, post.getContent(), null);
                }
                break;
            case R.id.lnLike : // see who like post
                this.toggleLike(true, (int)v.getTag());
                break;
            case R.id.lnComment : // comment click
                this.toggleComment(true, (int)v.getTag());
                break;
            case R.id.imgOption : // post option
                this.postOption((Post)v.getTag(R.id.key_post));
                break;
        }
    }
}