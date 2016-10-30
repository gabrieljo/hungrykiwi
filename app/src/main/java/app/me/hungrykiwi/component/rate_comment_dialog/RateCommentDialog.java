package app.me.hungrykiwi.component.rate_comment_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.write_rate_comment_dialog.WriteRateCommentDialog;

/**
 * rate comment dialog
 * Created by user on 10/26/2016.
 */
public class RateCommentDialog extends Dialog implements View.OnClickListener{

    int mode;
    int id;
    Context mContext;
    RateCommentAdapter adapter;

    public RateCommentDialog(@NonNull Context context, int mode) {
        super(context, R.style.anim_dialog);
        this.mContext = context;
        this.setContentView(R.layout.dialog_comment);
        this.getWindow().getAttributes().windowAnimations = R.style.anim_style;
        this.mode = mode;
        this.setSize();
        RecyclerView recycler = (RecyclerView)this.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        this.adapter = new RateCommentAdapter(context, this.mode);
        recycler.setAdapter(this.adapter);
        EditText editComment = (EditText)this.findViewById(R.id.editComment);
        editComment.setOnClickListener(this);

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

    public RateCommentDialog setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * fetch comment and show
     * @param id
     */
    public void show(int id) {
        if(this.adapter != null) {
            this.id = id;
            this.adapter.setId(id);
            this.adapter.refresh();
        }
        this.show();
    }

    public void refresh() {
        if(this.adapter != null)
            this.adapter.refresh();
        if(this.isShowing() == false) this.show();
    }

    public static class Mode {
        public static final int RECIPE_COMMENT = 0;
        public static final int RESTR_COMMENT = 1;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editComment:
                new WriteRateCommentDialog(this.mContext, WriteRateCommentDialog.Mode.RECIPE_WRITE, this.id).show();
                break;
        }
    }
}
