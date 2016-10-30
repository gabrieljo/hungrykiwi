package app.me.hungrykiwi.http.hungry_kiwi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import app.me.hungrykiwi.activities.login.LoginActivity;
import app.me.hungrykiwi.http.HttpMessage;
import app.me.hungrykiwi.utils.Message;
import app.me.hungrykiwi.utils.Utility;
import cz.msebera.android.httpclient.Header;

/**
 * define response from server
 * Created by user on 10/10/2016.
 */

public class HungryResponse extends JsonHttpResponseHandler{
    Context mContext;

    public HungryResponse(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        try {
            Log.d("INFO", response.toString(4));
        }catch(JSONException ex) {
            Log.d("INFO", ex.getMessage());
        }

    }


    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        // for testing when error comes out.
        Utility.goWeb(responseString, mContext);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Utility util = new Utility();
        switch (statusCode) {
            case 400 : // requested http not found
                util.toast(mContext, HttpMessage.getMessage(HttpMessage.Type.HTTP_NOT_FOUND));
                break;
            case 401 : // access_not_allowed & go back to login page
                util.toast(mContext, HttpMessage.getMessage(HttpMessage.Type.INVALID_TOKEN));
                Intent intent = new Intent(mContext, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("logout", true);
                mContext.startActivity(intent);
                break;
            case 500 : // internal server error
                util.toast(mContext, HttpMessage.getMessage(HttpMessage.Type.INTERNAL_ERROR));
                break;
            case 504 : // time -out
                util.toast(mContext, HttpMessage.getMessage(HttpMessage.Type.TIME_OUT));
                break;
        }
    }


}
