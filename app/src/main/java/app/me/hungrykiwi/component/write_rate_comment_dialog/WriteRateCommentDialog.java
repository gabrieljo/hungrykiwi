package app.me.hungrykiwi.component.write_rate_comment_dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.util.Util;

import java.util.List;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.recipe_detail.RecipeDetailActivity;
import app.me.hungrykiwi.service.RecipeService;
import app.me.hungrykiwi.service.RestrService;
import app.me.hungrykiwi.utils.Utility;

/**
 * write rate comment dialog
 * Created by user on 10/30/2016.
 */
public class WriteRateCommentDialog extends Dialog implements View.OnClickListener{
    int mode;
    int id;
    Context mContext;
    public WriteRateCommentDialog(Context context, int mode, int id) {
        super(context, R.style.anim_dialog);
        this.mContext =context;
        this.setContentView(R.layout.dialog_write_rate_comment);
        this.getWindow().getAttributes().windowAnimations = R.style.anim_style;

        this.mode = mode;
        this.id = id; // restaurant or recipe id

        this.setSize(); // set size

        this.initView();
    }

    /**
     * set width and height of comment dialog to match parent
     */
    public void setSize() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(this.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);
    }

    /**
     * init views
     */
    public void initView() {
        final TextView textEval = (TextView)this.findViewById(R.id.textEval);
        ((RatingBar)this.findViewById(R.id.ratingbar)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                try {
                    switch ((int) v) {
                        case 1:
                            textEval.setText("Worst");
                            break;
                        case 2:
                            textEval.setText("Bad");
                            break;
                        case 3:
                            textEval.setText("Average");
                            break;
                        case 4:
                            textEval.setText("Yummy");
                            break;
                        case 5:
                            textEval.setText("Best");
                            break;
                    }
                } catch(Exception ex) {
                    Log.d("INFO", "WriteRateCommentDialog : "+ex.getMessage());
                }
            }
        });
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }


    public void store() {
        String comment = ((EditText)this.findViewById(R.id.editComment)).getText().toString();
        if(comment == null || comment.length() ==0 ) {
            new Utility().toast(this.getContext(), "Please type in the comment");
            return;
        }
        switch (this.mode) {
            case Mode.RECIPE_WRITE : // recipe comment
                new RecipeService(this.mContext).storeComment(comment, this.id, (int)((RatingBar)this.findViewById(R.id.ratingbar)).getRating());
                this.dismiss();
                break;
            case Mode.RESTR_WRITE : // restaurant comment
                new RestrService(this.getContext()).storeComment(comment, this.id);
                this.dismiss();
                break;
        }
    }

    /**
     * click event
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave : // save dialog
                store();
                break;
            case R.id.btnCancel : // cancel dialog
                this.dismiss();
                break;
        }
    }

    /**
     * mode for write comment
     */
    public class Mode {
        public static final int RESTR_WRITE = 1;
        public static final int RECIPE_WRITE = 2;
    }
}
