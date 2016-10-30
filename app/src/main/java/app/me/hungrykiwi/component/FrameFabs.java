package app.me.hungrykiwi.component;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.utils.AnimationEffect;

/**
 * fab group
 * Created by user on 10/25/2016.
 */
public class FrameFabs extends FrameLayout{
    ObjectAnimator[] movers;
    FloatingActionButton fabMain;
    Animation animShow, animHide;
    public FrameFabs(Context context) {
        super(context);
    }

    public FrameFabs(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * add fabs
     * @param title
     * @param rid
     * @param listeners
     */
    public void addFab(String[] title, @DrawableRes int[] rid, View.OnClickListener[] listeners, FloatingActionButton fabMain) {
        this.fabMain = fabMain;
        this.animShow = AnimationUtils.loadAnimation(this.getContext(), R.anim.main_fab_show);
        this.animHide = AnimationUtils.loadAnimation(this.getContext(), R.anim.main_fab_hide);
        this.animShow.setFillAfter(true);
        this.animHide.setFillAfter(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        int count = title.length;
        this.movers = new ObjectAnimator[count];
        AnimationEffect util = new AnimationEffect();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        int margin = getResources().getDimensionPixelOffset(R.dimen.fab_margin) + 16;
        params.bottomMargin = margin;
        params.rightMargin = margin;

        for(int i=0; i< count; i++) {
            View view = LayoutInflater.from(this.getContext()).inflate(R.layout.fab_single, null);

            view.setLayoutParams(params);
            TextView text = (TextView)view.findViewById(R.id.text);
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            text.setText(title[i]);
            fab.setImageResource(rid[i]);
            view.setOnClickListener(listeners[i]);
            this.addView(view);
            this.movers[i] = util.translateY(view, 0, -50 -150 * (i+1), 200);
        }

    }

    /**
     * toggle
     */
    public void toggle() {
        if(movers !=null && movers.length != 0) {
            int count = movers.length;
            boolean show = !this.isShown();
            if(show == true ) {
                this.fabMain.startAnimation(this.animShow);
                this.setVisibility(VISIBLE);
                for(int i=0; i< count; i++) {
                    this.movers[i].start();
                }
            } else {
                this.fabMain.startAnimation(this.animHide);
                this.setVisibility(GONE);
            }
        }
    }


}