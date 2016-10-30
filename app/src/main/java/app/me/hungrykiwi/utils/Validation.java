package app.me.hungrykiwi.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 10/6/2016.
 */

public class Validation {
    public static boolean isEmpty(TextView v) {
        int leng = v.getText().toString().length();
        if(leng == 0) return true;
        return false;
    }
    public static boolean isEmpty(EditText v) {
        int leng = v.getText().toString().length();
        if(leng == 0) return true;
        return false;
    }
    public static boolean isEmpty(View v) {
        if(v instanceof TextView)
            return isEmpty((TextView)v);
        else if(v instanceof EditText) {
            return isEmpty((EditText)v);
        }
        return true;
    }
}
