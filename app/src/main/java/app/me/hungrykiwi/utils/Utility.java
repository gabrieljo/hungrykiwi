package app.me.hungrykiwi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import app.me.hungrykiwi.activities.WebDemo;

/**
 * Created by user on 10/3/2016.
 */

public class Utility {

    public static void goWeb(String data, Context context) {
        Intent intent = new Intent(context, WebDemo.class).setType(data);
        ((Activity)context).startActivity(intent);
    }

    public void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


}
