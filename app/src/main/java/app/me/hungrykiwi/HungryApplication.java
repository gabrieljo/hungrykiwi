package app.me.hungrykiwi;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

import app.me.hungrykiwi.utils.Utility;

/**
 * Created by peterlee on 2016-09-11.
 */
public class HungryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        AppEventsLogger.activateApp(this);
//        FacebookSdk.sdkInitialize(this);
//        FacebookSdk.setIsDebugEnabled(true);
//        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
