package app.me.hungrykiwi.component.comment_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.pages.post.PostAdapter;
import app.me.hungrykiwi.service.PostService;
import app.me.hungrykiwi.utils.Message;
import app.me.hungrykiwi.utils.Utility;

/**
 * comment dialog
 * Created by user on 10/14/2016.
 */
public class CommentDialog extends Dialog implements View.OnClickListener{
    CommentAdapter adapter;
    PostAdapter postAdapter;
    int postId, holderId;
    public CommentDialog(Context context, PostAdapter postAdapter, int holderId) {
        super(context, R.style.anim_dialog);
        this.setContentView(R.layout.dialog_comment);
        this.getWindow().getAttributes().windowAnimations = R.style.anim_style;
        this.postAdapter = postAdapter;
        this.holderId = holderId;


        this.initView();

        this.setSize(); // set size
    }


    /**
     * init views
     */
    public void initView() {
        this.adapter = new CommentAdapter(this.getContext(), this.holderId, this.postAdapter);
        RecyclerView recycler = (RecyclerView)this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(this.adapter);

        this.findViewById(R.id.editComment).setOnClickListener(this);
        this.findViewById(R.id.frameEnter).setOnClickListener(this);
    }

    /**
     * set width and height of comment dialog to match parent
     */
    public void setSize() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
    }

    /**
     * show comment dialog
     * @param postId
     */
    public void show(int postId) {
        this.show();
        this.postId = postId;
        this.adapter.read(postId);

    }

    /**
     * store comment
     * @param comment
     */
    public void store(String comment) {
        if(comment != null && comment.length() != 0) {
            new PostService(this.getContext()).storeComment(this.postId, this.holderId, comment, this.adapter, this.postAdapter);
            ((EditText)this.findViewById(R.id.editComment)).setText(null);
        } else {
            new Utility().toast(this.getContext(), Message.getMessage(Message.Type.PROBLEM_STRING_EMPTY));
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        this.adapter.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frameEnter : // comment store
                this.store(((EditText)this.findViewById(R.id.editComment)).getText().toString());
                break;
        }
    }
}
