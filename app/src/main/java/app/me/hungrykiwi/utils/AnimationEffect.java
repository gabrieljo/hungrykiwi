package app.me.hungrykiwi.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by user on 10/10/2016.
 */

public class AnimationEffect {

    public ObjectAnimator translateY(View v, float start, float end, long duration) {
        ObjectAnimator mover = ObjectAnimator.ofFloat(v, "translationY", start, end);
        mover.setDuration(duration);
        return mover;
    }

    public Animation loadAnim(Context context, @AnimRes int res) {
        return AnimationUtils.loadAnimation(context, res);
    }
}
