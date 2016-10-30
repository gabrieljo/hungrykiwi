package app.me.hungrykiwi.component.post_like_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import app.me.hungrykiwi.R;

/**
 * like dialog
 * Created by user on 10/14/2016.
 */
public class LikeDialog extends Dialog {
    LikeAdapter adapter;
    int postId;
    public LikeDialog(Context context) {
        super(context, R.style.anim_dialog);
        this.setContentView(R.layout.dialog_like);
        this.getWindow().getAttributes().windowAnimations = R.style.anim_style;
        this.initView();

        this.setSize(); // set size
    }


    /**
     * init views
     */
    public void initView() {
        this.adapter = new LikeAdapter(this.getContext());
        RecyclerView recycler = (RecyclerView)this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(this.adapter);
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

    @Override
    public void dismiss() {
        super.dismiss();
        this.adapter.clear();
    }
}
