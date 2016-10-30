package app.me.hungrykiwi.component.viewutil;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by user on 10/6/2016.
 */
public class FabBehavior extends FloatingActionButton.Behavior{
    ScaleAnimation scaleIn, scaleOut;

    public FabBehavior(Context context, AttributeSet attrs) {
        super();
        this.scaleIn = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        this.scaleOut = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        this.scaleIn.setFillAfter(true);
        this.scaleOut.setFillAfter(true);
        this.scaleIn.setDuration(200);
        this.scaleOut.setDuration(200);
    }

    /**
     * fab reaction to scroll down or up
     * @param coordinatorLayout
     * @param fab
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton fab, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, fab, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (fab.getVisibility() == View.VISIBLE && dyConsumed > 0) { // SHOW
            this.toggleFab(false, fab);
        }
        else if (fab.getVisibility() == View.GONE && dyConsumed < 0){ // HIDE
            this.toggleFab(true, fab);
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     * toggle fab
     * @param show
     */
    public void toggleFab(boolean show, FloatingActionButton fab) {

        if(show == true) {
            fab.startAnimation(this.scaleIn);
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.startAnimation(this.scaleOut);
            fab.setVisibility(View.GONE);
        }
    }
}