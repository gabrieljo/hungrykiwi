package app.me.hungrykiwi.component;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import app.me.hungrykiwi.R;
import android.view.ViewGroup.LayoutParams;

/**
 * progress dialog
 * Created by user on 10/6/2016.
 */
public class Progress{

    public static Dialog progress;

    public static void on(Context context) {
        try {
            Progress.progress = new Dialog(context, R.style.progress_dialog);
            ProgressBar pb = new ProgressBar(context);
            LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            Progress.progress.setContentView(pb, param);
            Progress.progress.setCancelable(false);
            Progress.progress.show();
        } catch(Exception ex){
            Log.d("INFO", "Progress : "+ex.getMessage());
        }
    }

    public static void off() {
        try {
            Progress.progress.dismiss();
            Progress.progress = null;
        } catch(Exception ex){
            Log.d("INFO", "Progress : "+ex.getMessage());
        }
    }

}
