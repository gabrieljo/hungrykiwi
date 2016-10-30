package app.me.hungrykiwi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import app.me.hungrykiwi.activities.WebDemo;

import static android.R.attr.data;

/**
 * Created by user on 10/3/2016.
 */

public class Utility {

    public static void goWeb(String data, Context context) {
        Intent intent = new Intent(context, WebDemo.class).setType(data);
        context.startActivity(intent);
    }

    /**
     * toast mesasge
     * @param context
     * @param text
     */
    public void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }



    public Point getScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * get screen width
     * @return
     */
    public int screenWidth(Context context) {
        Point point = this.getScreen(context);
        return point.x;
    }


    /**
     * get screen height
     * @return
     */
    public int screenHeight(Context context) {
        Point point = this.getScreen(context);
        return point.y;
    }


    /**
     * @return bitmap (from given string)
     */
    public Bitmap uriTobitmap(Context context, Uri uri, int width, int height){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            Matrix matrix = new Matrix();
            matrix.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new RectF(0, 0, width, height * 4 / 5), Matrix.ScaleToFit.CENTER);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }catch(IOException ex) {
            Log.d("INFO", "Utility image bitmap : "+ex.getMessage());
        }
        return bitmap;
    }

    /**
     * calc past time
     * @param past
     * @return
     */
    public static String calcPastTime(String past) {
        int pastYear, pastMonth, pastDay, pastHour, pastMin, pastSec; // past
        int currYear, currMonth, currDay, currHour, currMin, currSec; // current
        long diffSec;

        int minSec = 60;
        int hourSec = 60 * minSec;
        int daySec = 24 * hourSec;
        int monthSec = 30 * daySec;
        int yearSec = 12 * monthSec;
        String result;
        String[] pasts = past.split(" ");
        String[] currents = getCurrent().split(" ");

        String[] pastDayUnit = pasts[0].split("-");
        String[] pastTimeUnit = pasts[1].split(":");
        String[] currDayUnit = currents[0].split("-");
        String[] currTimeUnit = currents[1].split(":");

        pastYear = Integer.valueOf(pastDayUnit[0]);
        pastMonth = Integer.valueOf(pastDayUnit[1]);
        pastDay = Integer.valueOf(pastDayUnit[2]);
        pastHour = Integer.valueOf(pastTimeUnit[0]);
        pastMin = Integer.valueOf(pastTimeUnit[1]);
        pastSec = Integer.valueOf(pastTimeUnit[2]);

        currYear = Integer.valueOf(currDayUnit[0]);
        currMonth = Integer.valueOf(currDayUnit[1]);
        currDay = Integer.valueOf(currDayUnit[2]);
        currHour = Integer.valueOf(currTimeUnit[0]);
        currMin = Integer.valueOf(currTimeUnit[1]);
        currSec = Integer.valueOf(currTimeUnit[2]);

        if(currYear != pastYear) currMonth += 12 * (currYear - pastYear);
        if(currMonth != pastMonth) currDay += 30 * (currMonth - pastMonth);
        if(currDay != pastDay) currHour += 24 * (currDay - pastDay);
        if(currHour != pastHour) currMin += 60 * (currHour - pastHour);
        if(currMin != pastMin) currSec += 60 * (currMin - pastMin);
        diffSec = currSec - pastSec;

        if(diffSec > yearSec) result = diffSec / yearSec +" year ago";
        else if(diffSec > monthSec) result = diffSec / monthSec +" month ago";
        else if(diffSec > daySec) result = diffSec / daySec +" day ago";
        else if(diffSec > hourSec) result = diffSec / hourSec +" hour ago";
        else if(diffSec > minSec) result = diffSec / minSec +" minute ago";
        else result = diffSec +" second ago";

        return result;
    }


    /**
     * get current time
     * @return
     */
    public static String getCurrent() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));

        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }


}
