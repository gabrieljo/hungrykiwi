package app.me.hungrykiwi.component.share_sns_dialog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import app.me.hungrykiwi.R;

/**
 * share sns dialog
 * Created by user on 10/17/2016.
 */
public class ShareSNSDialog extends AlertDialog.Builder implements View.OnClickListener{
    String title;
    String content;
    String url;
    Fragment fragment;
    public ShareSNSDialog(@NonNull Context context, Fragment fragment) {
        super(context);
        this.fragment = fragment;
    }

    /**
     * show share dialog
     * @param title
     * @param content
     * @param url
     */
    public void show(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;

        View view =LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_share_sns, null);
        this.setView(view);
        this.setTitle("Share");
        view.findViewById(R.id.fmFace).setOnClickListener(this);
        this.create().show();
    }

    /**
     * share facebook
     */
    public void shareFace() {
        try {
            ShareDialog share = null;
            if (this.getContext() instanceof Activity) {
                share = new ShareDialog(((Activity) this.getContext()));
            } else if(this.fragment != null){
                share = new ShareDialog(this.fragment);
            }

            share.registerCallback(CallbackManager.Factory.create(), new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent.Builder builder = new ShareLinkContent.Builder();

                if(this.title != null) builder.setContentTitle(this.title);
                if(this.content != null) builder.setContentDescription(this.content);
                if(this.url != null) builder.setContentUrl(Uri.parse(this.url));
//                builder.setContentTitle("abc");
//                builder.setContentDescription("sbd");
//                builder.setContentUrl(Uri.parse("http://developers.facebook.com/android"));
                share.show(builder.build());
            }
        }catch(Exception ex) {
            Log.d("INFO", "ShareSNSDialog Error : "+ex.getMessage());
        }
    }

    /**
     * click event
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fmFace :
                this.shareFace();
                break;
        }
    }

    @Override
    public String toString() {
        return "TITLE : "+this.title
                +"\nCONTENT : "+this.content
                +"\nURI : "+this.url+"\n";
    }
}
